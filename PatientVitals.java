public class PatientVitals {

    private double[] readings;
    private int count;

    public PatientVitals(double[] initialReadings) {

        readings = new double[500];
        count = 0;

        for (double reading : initialReadings) {
            recordReading(reading);
        }
    }

    public void recordReading(double reading) {

        if (reading <= 0 || reading > 45) {
            return;
        }

        if (count < 500) {
            readings[count] = reading;
            count++;
        }
    }

    public double getAverage() {

        if (count == 0) {
            return 0;
        }

        double sum = 0;

        for (int i = 0; i < count; i++) {
            sum += readings[i];
        }

        return sum / count;
    }

    public double[] getAllReadings() {

        double[] result = new double[count];

        for (int i = 0; i < count; i++) {
            result[i] = readings[i];
        }

        return result;
    }

    public static void main(String[] args) {

        PatientVitals v =
            new PatientVitals(
                new double[]{36.5, -2, 37.1}
            );

        double[] values = v.getAllReadings();

        for (double value : values) {
            System.out.print(value + " ");
        }

        System.out.println();

        values[0] = 999;

        double[] copy = v.getAllReadings();

        System.out.println(copy[0]);
    }
}