#ifndef GPS_JOURNEY_H
#define GPS_JOURNEY_H

#include <string>
#include <vector>

#include "types.h"
#include "timedwaypoint.h"

namespace GPS
{
  class Journey
  {
    protected:
      const std::vector<TimedWaypoint> points;

    public:
      Journey(std::vector<TimedWaypoint>);



      /* The increase in height from the starting waypoint to the finishing waypoint.
       * Returns zero if the height difference is negative.
       * Throws a std::domain_error if the journey contains fewer than two waypoints.
       */
      metres netHeightGain() const;



      /* The sum of all the positive height differences between successive waypoints.
       * That is, downhill changes are ignored.
       * Throws a std::domain_error if the journey contains fewer than two waypoints.
       */
      metres totalHeightGain() const;



      /* The fastest speed between successive waypoints, taking into account both vertical and
       * horizontal distance travelled.
       * Throws a std::domain_error if the journey contains fewer than two waypoints.
       * Throws a std::domain_error if the time elapsed between any two adjacent points is zero or negative.
       */
      metresPerSecond maxSpeed() const;



      /* The waypoint in the journey with the highest altitude.
       * If two points are equally highest, then the one that comes later is returned.
       * Throws a std::domain_error if the journey contains zero waypoints.
       */
      Waypoint highestWaypoint() const;



      /* The waypoint in the journey with the lowest altitude.
       * If two points are equally lowest, then the one that comes later is returned.
       * Throws a std::domain_error if the journey contains zero waypoints.
       */
      Waypoint lowestWaypoint() const;


  };
}

#endif
