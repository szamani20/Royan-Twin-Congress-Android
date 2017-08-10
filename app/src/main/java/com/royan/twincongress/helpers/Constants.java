package com.royan.twincongress.helpers;

/**
 * Created by szamani on 7/29/2017.
 */

public class Constants {
    public static final int SCC_CONGRESS = 0;
    public static final int RBC_CONGRESS = 1;
    public static final int NURSE_CONGRESS = 2;

    public static final int IS_SPEAKER = 0;
    public static final int OP_SPEAKER = 1;
    public static final int POSTER_SPEAKER = 2;

    public static final int WINNER_INTERNATIONAL = 0;
    public static final int WINNER_NATIONAL = 1;

    public static final int COMPANY_SPONSOR = 0;
    public static final int COMPANY_ORDINARY = 1;

    public static final int ITEM_BANNER = 2;
    public static final int ITEM_BOOKMARKS = 3;
    public static final int ITEM_AGENDA = 4;
    public static final int ITEM_COMPANIES = 5;
    public static final int ITEM_SPONSORS = 6;
    public static final int ITEM_MAP = 7;
    public static final int ITEM_UPDATE = 8;

    public static final int BACKGROUND = 0;
    public static final int OBJECTIVE = 1;
    public static final int METHOD = 2;
    public static final int RESULT = 3;
    public static final int CONCLUSION = 4;
    public static final int KEYWORD = 5;
    public static final int ABSTRACT_SIZE = 6;

    public static final String SPEAKER_ID = "SPEAKER_ID";

    public static final int SPEAKER_FETCH_SIZE = 20;
    public static final String SEARCH_CRITERIA = "SEARCH_CRITERIA";
    public static final String WINNER_ID = "WINNER_ID";
    public static final String WINNER_TYPE = "WINNER_TYPE";
    public static int[][] SPEAKER_FETCH_OFFSET = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0},
    };

    public static final int COMPANY_FETCH_SIZE = 20;
    public static final int[] COMPANY_FETCH_OFFSET = {
            0, 0
    };

    public static final String CONGRESS_TYPE = "CONGRESS_TYPE";
    public static final String SPEAKER_TYPE = "SPEAKER_TYPE";
}
