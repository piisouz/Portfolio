#include "journey.h"
#include <stdexcept>
#include <cmath>

namespace GPS
{

Journey::Journey(std::vector<TimedWaypoint> timedWaypoints) : points{timedWaypoints} {}


// TODO: Stub definition needs implementing
metres Journey::netHeightGain() const
{
    //makes the requirement of having at least two waypoints
    if (points.size() < 2)
    {
        throw std::domain_error("A journey must contain at least two waypoints to calculate height gain.");
    }

    //calculate the difference between the final altitude and starting altitude

    metres startAltitude = points.front().waypoint.altitude();
    metres endAltitude = points.back().waypoint.altitude();
    metres difference = endAltitude - startAltitude;

    // Return the difference if positive if not returns 0 
    if (difference < 0.0)
    {
        return 0.0;
    }

    return difference;
}

// TODO: Stub definition needs implementing
metres Journey::totalHeightGain() const
{
    //makes the requirement of having at least two waypoints
    if (points.size() < 2)
    {
        throw std::domain_error("A journey must contain at least two waypoints to calculate total height gain.");
    }

    metres totalGain = 0.0;

    //loops through points and sum up the altitude changes
    for (size_t i = 1; i < points.size(); ++i)
    {
        metres prevAltitude = points[i - 1].waypoint.altitude();
        metres currAltitude = points[i].waypoint.altitude();
        metres difference = currAltitude - prevAltitude;

        if (difference > 0.0)
        {
            totalGain += difference;
        }
    }

    return totalGain;
}

// TODO: Stub definition needs implementing
metresPerSecond Journey::maxSpeed() const
{
    // makes the requirement of at least 2 waypoints
    if (points.size() < 2)
    {
        throw std::domain_error("A journey must contain at least two waypoints to calculate max speed.");
    }

    metresPerSecond maximumSpeed = 0.0;

    //loops through points
    for (size_t i = 1; i < points.size(); ++i)
    {
        const TimedWaypoint& prev = points[i - 1];
        const TimedWaypoint& curr = points[i];

        //does the time validation
        std::time_t timeDiff = curr.timeStamp - prev.timeStamp;
        if (timeDiff <= 0)
        {
            throw std::domain_error("Time elapsed between adjacent points must be greater than zero.");
        }

        // calculates the 3D distance
        metres hDist = Waypoint::horizontalDistanceBetween(prev.waypoint, curr.waypoint);
        metres vDist = Waypoint::verticalDistanceBetween(prev.waypoint, curr.waypoint);
        metres totalDist = std::sqrt(std::pow(hDist, 2) + std::pow(vDist, 2));

        //calculates speed
        metresPerSecond speed = totalDist / static_cast<double>(timeDiff);

        //tracks the maximum
        if (speed > maximumSpeed)
        {
            maximumSpeed = speed;
        }
    }

    return maximumSpeed;
}

// TODO: Stub definition needs implementing
Waypoint Journey::highestWaypoint() const
{
    //throws domain error if there are no waypoints in the journey
    if (points.empty())
    {
        throw std::domain_error("Cannot determine the highest waypoint of an empty journey.");
    }

    //tracks the highest waypoint found 
    Waypoint highest = points.front().waypoint;

    //loop through and find to maximum altitude
    for (size_t i = 1; i < points.size(); ++i)
    {
        if (points[i].waypoint.altitude() > highest.altitude())
        {
            highest = points[i].waypoint;
        }
    }

    return highest;
}

// TODO: Stub definition needs implementing
Waypoint Journey::lowestWaypoint() const
{
    //throw domain error if there are no waypoints in the journey
    if (points.empty())
    {
        throw std::domain_error("Cannot determine the lowest waypoint of an empty journey.");
    }

    //tracks the lowest waypoint found 
    Waypoint lowest = points.front().waypoint;

    //loop through and find the minimum altitude
    for (size_t i = 1; i < points.size(); ++i)
    {
        if (points[i].waypoint.altitude() < lowest.altitude())
        {
            lowest = points[i].waypoint;
        }
    }

    return lowest;
}


}
