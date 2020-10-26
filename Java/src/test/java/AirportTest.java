import aircompany.Airport;
import aircompany.models.ExperimentalPlane;
import aircompany.types.ClassificationLevel;
import aircompany.types.ExperimentalType;
import aircompany.types.MilitaryType;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import aircompany.models.MilitaryPlane;
import aircompany.models.PassengerPlane;
import aircompany.models.Plane;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AirportTest {

    private List<Plane> planes = Arrays.asList(
            new PassengerPlane("Boeing-737", 900, 12000, 60500,
                    164),
            new PassengerPlane("Boeing-737-800", 940, 12300, 63870,
                    192),
            new PassengerPlane("Boeing-747", 980, 16100, 70500,
                    242),
            new PassengerPlane("Airbus A320", 930, 11800, 65500,
                    188),
            new PassengerPlane("Airbus A330", 990, 14800, 80500,
                    222),
            new PassengerPlane("Embraer 190", 870, 8100, 30800,
                    64),
            new PassengerPlane("Sukhoi Superjet 100", 870, 11500, 50500,
                    140),
            new PassengerPlane("Bombardier CS300", 920, 11000, 60700,
                    196),
            new MilitaryPlane("B-1B Lancer", 1050, 21000, 80000,
                    MilitaryType.BOMBER),
            new MilitaryPlane("B-2 Spirit", 1030, 22000, 70000,
                    MilitaryType.BOMBER),
            new MilitaryPlane("B-52 Stratofortress", 1000, 20000, 80000,
                    MilitaryType.BOMBER),
            new MilitaryPlane("F-15", 1500, 12000, 10000,
                    MilitaryType.FIGHTER),
            new MilitaryPlane("F-22", 1550, 13000, 11000,
                    MilitaryType.FIGHTER),
            new MilitaryPlane("C-130 Hercules", 650, 5000, 110000,
                    MilitaryType.TRANSPORT),
            new ExperimentalPlane("Bell X-14", 277, 482, 500,
                    ExperimentalType.HIGH_ALTITUDE, ClassificationLevel.SECRET),
            new ExperimentalPlane("Ryan X-13 Vertijet", 560, 307, 500,
                    ExperimentalType.VTOL, ClassificationLevel.TOP_SECRET)
    );

    private PassengerPlane planeWithMaxPassengerCapacity = new PassengerPlane("Boeing-747", 980,
            16100, 70500, 242);

    private Airport airport;

    @BeforeTest
    public void setUp() {
        airport = new Airport(planes);
    }

    @Test
    public void testGetTransportMilitaryPlanes() {

        List<MilitaryPlane> transportMilitaryPlanes = airport.getTransportMilitaryPlane();
        Assert.assertFalse(transportMilitaryPlanes.isEmpty(), "Check method getTransportMilitaryPlane");
    }

    @Test
    public void testGetPassengerPlaneWithMaxPassengerCapacity() {

        PassengerPlane expectedPlaneWithMaxPassengersCapacity = airport.getPassengerPlaneWithMaxPassengersCapacity();
        Assert.assertEquals(planeWithMaxPassengerCapacity, expectedPlaneWithMaxPassengersCapacity,
                "Check method getPassengerPlaneWithMaxPassengersCapacity");
    }

    @Test
    public void testSortByMaxLoadCapacity() {

        List<? extends Plane> planesSortedByMaxLoadCapacity = airport.sortByMaxLoadCapacity().getPlanes();
        planes.sort(Comparator.comparingInt(Plane::getMaxLoadCapacity));
        Assert.assertTrue(Arrays.equals(planesSortedByMaxLoadCapacity.toArray(), planes.toArray()),
                "Check method sortByMaxLoadCapacity");
    }

    @Test
    public void testHasAtLeastOneBomberInMilitaryPlanes() {

        List<MilitaryPlane> bomberMilitaryPlanes = airport.getBomberMilitaryPlane();
            Assert.assertFalse(bomberMilitaryPlanes.isEmpty(),
                    "Check method getBomberMilitaryPlane");
    }

    @Test
    public void testExperimentalPlanesHasClassificationLevelHigherThanUnclassified() {

        List<ExperimentalPlane> experimentalPlanes = airport.getExperimentalPlane();
        List<ExperimentalPlane> collect = experimentalPlanes.stream().filter(experimentalPlane ->
                experimentalPlane.getClassificationLevel().ordinal() >
                        ClassificationLevel.UNCLASSIFIED.ordinal()).collect(Collectors.toList());
        Assert.assertEquals(experimentalPlanes.size(), collect.size(),
                "Check that classification level of experimental planes is higher than UNCLASSIFIED");
    }
}