class BusRoute {

    String routeCode;
    String routeName;
    int priority;

    public BusRoute(String routeCode,
                    String routeName,
                    int priority) {

        this.routeCode = routeCode;
        this.routeName = routeName;
        this.priority = priority;
    }

    public BusRoute(String routeCode,
                    String routeName) {

        this(routeCode, routeName, 5);
    }

    int compareTo(BusRoute other) {

        // Lower priority number comes first
        if (this.priority != other.priority) {
            return this.priority - other.priority;
        }

        // If priority is same, compare route code
        int codeResult =
                this.routeCode.compareToIgnoreCase(other.routeCode);

        if (codeResult != 0) {
            return codeResult;
        }

        // If code is also same, compare route name
        return this.routeName.compareToIgnoreCase(other.routeName);
    }

    static BusRoute[] rankRoutes(BusRoute[] routes) {

        BusRoute[] result =
                new BusRoute[routes.length];

        for (int i = 0; i < routes.length; i++) {
            result[i] = routes[i];
        }

        // Bubble sort
        for (int i = 0; i < result.length - 1; i++) {

            for (int j = 0; j < result.length - 1 - i; j++) {

                if (result[j].compareTo(result[j + 1]) > 0) {

                    BusRoute temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {

        BusRoute[] routes = {
            new BusRoute("RT205L",
                    "Airport Express", 3),

            new BusRoute("rt201j",
                    "City Central", 4),

            new BusRoute("RT299T",
                    "Night Service")
        };

        BusRoute[] ranked =
                rankRoutes(routes);

        System.out.println("Ranked routes:");

        for (BusRoute route : ranked) {
            System.out.println(
                    route.routeCode + " - "
                    + route.routeName
                    + " - Priority "
                    + route.priority);
        }
    }
}