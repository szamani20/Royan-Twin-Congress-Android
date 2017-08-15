package com.royan.twincongress.interfaces;

import com.royan.twincongress.models.Company;
import com.royan.twincongress.models.Event;
import com.royan.twincongress.models.Speaker;
import com.royan.twincongress.models.Winner;

import java.util.List;

/**
 * Created by szamani on 8/5/2017.
 */

public interface OnFirstDownloadTaskListener {
    void onSpeakerFetchTaskCompleted(List<List<Speaker>> speakerList,
                                     final List<List<Winner>> winnerList,
                                     List<List<Company>> companiesData,
                                     final List<Event> eventList);
}
