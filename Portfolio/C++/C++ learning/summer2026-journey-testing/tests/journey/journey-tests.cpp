#define BOOST_TEST_MODULE JourneyTests
#include <boost/test/included/unit_test.hpp>

#include <vector>
#include <stdexcept>

#include "journey.h"
#include "timedwaypoint.h"

using namespace GPS;


//  keep coordinates and time at 0
TimedWaypoint makePoint(metres altitude)
{
    return TimedWaypoint(Waypoint(0.0, 0.0, altitude), 0);
}

BOOST_AUTO_TEST_SUITE( JourneyNetHeightGainTests )


// 1st test a  uphill hike
// final altitude minus starting altitude

BOOST_AUTO_TEST_CASE( NetHeightGain_StandardClimb_ReturnsPositiveGain )
{
    
    std::vector<TimedWaypoint> points = {
        makePoint(100.0),
        makePoint(150.0),
        makePoint(250.0)
    };
    Journey journey(points);

    // grabss the calculated gain
    metres result = journey.netHeightGain();

    // The net difference should be 150m from the subtraction
    
    BOOST_CHECK_CLOSE(result, 150.0, 0.001);
}


// 2nd test  a loop that starts and ends at the exact same elevation the net gain should be zero

BOOST_AUTO_TEST_CASE( NetHeightGain_FlatJourney_ReturnsZero )
{
    // start at 100m and then to climb to 120m but finish back down at 100m
    std::vector<TimedWaypoint> points = {
        makePoint(100.0),
        makePoint(120.0),
        makePoint(100.0)
    };
    Journey journey(points);

    // grab the calculated gain
    metres result = journey.netHeightGain();

    
    
    BOOST_CHECK_SMALL(result, 0.001);
}


// 3rd test a downhill journey 
//if net change is negative the function must return 0

BOOST_AUTO_TEST_CASE( NetHeightGain_NetDescent_ReturnsZero )
{
    // start high at 200m and finish low at 50m
    std::vector<TimedWaypoint> points = {
        makePoint(200.0),
        makePoint(150.0),
        makePoint(50.0)
    };
    Journey journey(points);

    // Grab the calculated gain
    metres result = journey.netHeightGain();

    
    BOOST_CHECK_SMALL(result, 0.001);
}


// 4th test to few points 


BOOST_AUTO_TEST_CASE( NetHeightGain_OneWaypoint_ThrowsDomainError )
{
    // set up an invalid journey with only a singlular waypoint
    std::vector<TimedWaypoint> points = {
        makePoint(100.0)
    };
    Journey journey(points);

    
    BOOST_CHECK_THROW(journey.netHeightGain(), std::domain_error);
}


// 5th test empty journey which should fail


BOOST_AUTO_TEST_CASE( NetHeightGain_EmptyJourney_ThrowsDomainError )
{
    // create an empty points list
    std::vector<TimedWaypoint> points = {};
    Journey journey(points);

    
    BOOST_CHECK_THROW(journey.netHeightGain(), std::domain_error);
}

BOOST_AUTO_TEST_SUITE_END()




BOOST_AUTO_TEST_SUITE(JourneyTotalHeightGainTests)

// tests a changing path to make sure it sums correctly
BOOST_AUTO_TEST_CASE(TotalHeightGain_FluctuatingClimb_ReturnsAccumulatedGain)
{
    std::vector<TimedWaypoint> points = {
        makePoint(100),  // Start at 100m
        makePoint(150), // Climb 50m
        makePoint(120), // Descend 30m (no gain added)
        makePoint(180)  // Climb 60m
    };
    Journey journey(points);
    
    BOOST_CHECK_CLOSE(journey.totalHeightGain(), 110.0, 0.001); // 50 + 60 = 110m
}

// tests descent
BOOST_AUTO_TEST_CASE(TotalHeightGain_PureDescent_ReturnsZero)
{
    std::vector<TimedWaypoint> points = {
        makePoint(200.0),
        makePoint(150.0),
        makePoint(100.0)
    };
    Journey journey(points);

    BOOST_CHECK_CLOSE(journey.totalHeightGain(), 0.0, 0.001);
}

// error handling for an empty journey
BOOST_AUTO_TEST_CASE(TotalHeightGain_EmptyJourney_ThrowsDomainError)
{
    std::vector<TimedWaypoint> points;
    Journey journey(points);

    BOOST_CHECK_THROW(journey.totalHeightGain(), std::domain_error);
}

BOOST_AUTO_TEST_SUITE_END()





BOOST_AUTO_TEST_SUITE(JourneyHighestWaypointTests)

// finds the highest point when it sits in the middle of a trip
BOOST_AUTO_TEST_CASE(HighestWaypoint_ValidJourney_ReturnsPointWithMaxAltitude)
{
    std::vector<TimedWaypoint> points = {
        makePoint(120.0),
        makePoint(350.0), 
        makePoint(210.0)
    };
    Journey journey(points);

    BOOST_CHECK_CLOSE(journey.highestWaypoint().altitude(), 350.0, 0.001);
}

// test error handling when the journey has no points
BOOST_AUTO_TEST_CASE(HighestWaypoint_EmptyJourney_ThrowsDomainError)
{
    std::vector<TimedWaypoint> points;
    Journey journey(points);

    BOOST_CHECK_THROW(journey.highestWaypoint(), std::domain_error);
}

BOOST_AUTO_TEST_SUITE_END()




BOOST_AUTO_TEST_SUITE(JourneyLowestWaypointTests)

// tests to find the lowest point when it sits in the middle of a trip
BOOST_AUTO_TEST_CASE(LowestWaypoint_ValidJourney_ReturnsPointWithMinAltitude)
{
    std::vector<TimedWaypoint> points = {
        makePoint(120.0),
        makePoint(45.0),
        makePoint(210.0)
    };
    Journey journey(points);

    BOOST_CHECK_CLOSE(journey.lowestWaypoint().altitude(), 45.0, 0.001);
}

// tests error handling when the journey has no points
BOOST_AUTO_TEST_CASE(LowestWaypoint_EmptyJourney_ThrowsDomainError)
{
    std::vector<TimedWaypoint> points;
    Journey journey(points);

    BOOST_CHECK_THROW(journey.lowestWaypoint(), std::domain_error);
}

BOOST_AUTO_TEST_SUITE_END()





BOOST_AUTO_TEST_SUITE(JourneyMaxSpeedTests)

// tests tracking the maximum speed 
BOOST_AUTO_TEST_CASE(MaxSpeed_ValidJourney_ReturnsFastestSegment)
{
  
    Waypoint w1(53.0, -1.0, 100.0);
    Waypoint w2(53.0, -1.0, 200.0); // 100m vertical climb
    Waypoint w3(53.0, -1.0, 250.0); // 50m vertical climb
    
    std::vector<TimedWaypoint> points = {
        {w1, 0},
        {w2, 10}, // 100m / 10s = 10 m/s
        {w3, 20}  //  50m / 10s = 5 m/s
    };
    Journey journey(points);

    BOOST_CHECK_CLOSE(journey.maxSpeed(), 10.0, 0.001);
}

// test error handling for an empty or short journey
BOOST_AUTO_TEST_CASE(MaxSpeed_EmptyJourney_ThrowsDomainError)
{
    std::vector<TimedWaypoint> points;
    Journey journey(points);

    BOOST_CHECK_THROW(journey.maxSpeed(), std::domain_error);
}

// test error handling for invalid time intervals
BOOST_AUTO_TEST_CASE(MaxSpeed_InvalidTimeInterval_ThrowsDomainError)
{
    Waypoint w1(53.0, -1.0, 100.0);
    Waypoint w2(53.0, -1.0, 200.0);
    
    std::vector<TimedWaypoint> points = {
        {w1, 10},
        {w2, 10} 
    };
    Journey journey(points);

    BOOST_CHECK_THROW(journey.maxSpeed(), std::domain_error);
}

BOOST_AUTO_TEST_SUITE_END()