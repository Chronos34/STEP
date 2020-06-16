// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public final class FindMeetingQuery {

  public List<Event> sortedEvents(Collection<Event> events) {
      List<Event> event = new ArrayList<> (events);
      Collections.sort(event, (Event a, Event b) -> TimeRange.ORDER_BY_START.compare(a.getWhen(), b.getWhen()) );
      return Collections.unmodifiableList(event);
  }

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    
    /* timeSlot is initialized with a whole day slot to be returned as
    an answer in the event no events are scheduled for that day. */
    TimeRange timeSlot = TimeRange.WHOLE_DAY;
    int eventDuration = (int) request.getDuration();

    Collection<TimeRange> answer = new ArrayList<>();

    if (events.size() == 0 && timeSlot.duration() >= eventDuration) {answer.add(timeSlot);}
    else if (events.size() != 0) {

        int end;
        int start = TimeRange.START_OF_DAY;

        events = sortedEvents(events);

        for (Event event : events) {

            /* The block below helps the function to determine when a scheduled event in
             'Collection<Event> events' should be ignored and have it's time range incorporated 
             into an available time slot for a meeting request. */
            if (Collections.disjoint(request.getAttendees(), event.getAttendees())) {

                /* The conditional block below helps the function to determine when to
                create a schedule that allows optional attendees to attend an event. */
                if (request.getAttendees().size() == 0) {

                    if (Collections.disjoint(request.getOptionalAttendees(), event.getAttendees())) {continue;}

                } 
                else if (answer.size() == 0) {continue;}
            }

            end = event.getWhen().start();
            timeSlot = TimeRange.fromStartEnd(start, end, false);
            if (timeSlot.duration() >= eventDuration) {answer.add(timeSlot);}

            // The line below ensures when start time is not set to a time frame that will
            // lead to an overlap with other important meetings for attendees.
            if (event.getWhen().end() >= start) {start = event.getWhen().end();}
        }

        timeSlot = TimeRange.fromStartEnd(start, TimeRange.END_OF_DAY, true);
        if (timeSlot.duration() >= eventDuration) {answer.add(timeSlot);}
    }

    return answer;
  }
}