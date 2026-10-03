import java.util.*;

interface Capability {
    String getName();

    boolean supports(String capabilityName);

    boolean setValue(Object value, String deviceName);
}

class PowerCapability implements Capability {

    private boolean on = false;

    @Override
    public String getName() {
        return "Power";
    }

    @Override
    public boolean supports(String capabilityName) {
        return capabilityName.equalsIgnoreCase("Power");
    }

    @Override
    public boolean setValue(
            Object value,
            String deviceName) {

        if (!(value instanceof Boolean)) {
            return false;
        }

        on = (Boolean) value;

        System.out.println(
                deviceName +
                ": " +
                (on ? "ON" : "OFF")
        );

        return true;
    }
}

class BrightnessCapability implements Capability {

    private int brightness = 0;

    @Override
    public String getName() {
        return "Brightness";
    }

    @Override
    public boolean supports(String capabilityName) {
        return capabilityName.equalsIgnoreCase("Brightness");
    }

    @Override
    public boolean setValue(
            Object value,
            String deviceName) {

        if (!(value instanceof Integer)) {
            return false;
        }

        int newBrightness = (Integer) value;

        if (newBrightness < 0 ||
                newBrightness > 100) {

            System.out.println(
                    "Rejected: " +
                    deviceName +
                    " brightness must be between 0% and 100%."
            );

            return false;
        }

        brightness = newBrightness;

        System.out.println(
                deviceName +
                ": brightness set to " +
                brightness +
                "%"
        );

        return true;
    }
}

class TemperatureCapability implements Capability {

    private int temperature = 24;

    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public boolean supports(String capabilityName) {
        return capabilityName.equalsIgnoreCase("Temperature");
    }

    @Override
    public boolean setValue(
            Object value,
            String deviceName) {

        if (!(value instanceof Integer)) {
            return false;
        }

        int newTemperature = (Integer) value;

        if (newTemperature < 16 ||
                newTemperature > 30) {

            System.out.println(
                    "Rejected: " +
                    deviceName +
                    " temperature must be between 16°C and 30°C."
            );

            return false;
        }

        temperature = newTemperature;

        System.out.println(
                deviceName +
                ": temperature set to " +
                temperature +
                "°C"
        );

        return true;
    }
}

class Device {

    private String name;

    private Map<String, Capability> capabilities =
            new LinkedHashMap<>();

    public Device(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addCapability(
            Capability capability) {

        capabilities.put(
                capability.getName().toLowerCase(),
                capability
        );

        System.out.println(
                name +
                ": " +
                capability.getName() +
                " capability added."
        );
    }

    public boolean hasCapability(
            String capabilityName) {

        return capabilities.containsKey(
                capabilityName.toLowerCase()
        );
    }

    public boolean apply(
            String capabilityName,
            Object value) {

        Capability capability =
                capabilities.get(
                        capabilityName.toLowerCase()
                );

        if (capability == null) {
            return false;
        }

        return capability.setValue(
                value,
                name
        );
    }
}

class SceneStep {

    private String capabilityName;
    private Object value;

    public SceneStep(
            String capabilityName,
            Object value) {

        this.capabilityName = capabilityName;
        this.value = value;
    }

    public int applyTo(
            List<Device> devices) {

        int count = 0;

        for (Device device : devices) {

            if (device.hasCapability(
                    capabilityName)) {

                if (device.apply(
                        capabilityName,
                        value)) {

                    count++;
                }
            }
        }

        return count;
    }
}

class Scene {

    private String name;

    private List<SceneStep> steps =
            new ArrayList<>();

    public Scene(String name) {
        this.name = name;
    }

    public void addStep(SceneStep step) {
        steps.add(step);
    }

    public void execute(
            List<Device> devices) {

        System.out.println(
                "Scene '" +
                name +
                "' started."
        );

        int totalActions = 0;

        for (SceneStep step : steps) {
            totalActions +=
                    step.applyTo(devices);
        }

        System.out.println(
                "Scene '" +
                name +
                "' completed: " +
                totalActions +
                " actions applied."
        );
    }
}

public class Problem3 {

    public static void main(String[] args) {

        Device labAC =
                new Device("Lab AC");

        labAC.addCapability(
                new PowerCapability()
        );

        labAC.addCapability(
                new TemperatureCapability()
        );

        Device ceilingLights =
                new Device("Ceiling Lights");

        ceilingLights.addCapability(
                new PowerCapability()
        );

        ceilingLights.addCapability(
                new BrightnessCapability()
        );

        Device projector =
                new Device("Projector");

        projector.addCapability(
                new PowerCapability()
        );

        List<Device> devices =
                Arrays.asList(
                        labAC,
                        ceilingLights,
                        projector
                );

        Scene lectureMode =
                new Scene("Lecture Mode");

        lectureMode.addStep(
                new SceneStep(
                        "Power",
                        true
                )
        );

        lectureMode.addStep(
                new SceneStep(
                        "Brightness",
                        40
                )
        );

        lectureMode.addStep(
                new SceneStep(
                        "Temperature",
                        24
                )
        );

        lectureMode.execute(devices);

        labAC.apply(
                "Temperature",
                12
        );

        projector.addCapability(
                new BrightnessCapability()
        );

        projector.apply(
                "Brightness",
                70
        );
    }
}