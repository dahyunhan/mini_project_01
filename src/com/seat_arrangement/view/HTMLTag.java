package com.seat_arrangement.view;

// html 태그 모음
public interface HTMLTag {
    String FRE_FRAME = "<!DOCTYPE html>\r\n"
            + "<html>\r\n"
            + "<head>\r\n"
            + "<title> 자리 배치</title>\r\n"
            + "</head>\r\n"
            + "\r\n"
            + "<body>\r\n"
            + "<table>";
    String POST_FRAME = "</table>\r\n"
            + "</body>\r\n"
            + "</html>";

    String OPEN_TR = "<tr>";
    String CLOSE_TR = "</tr>";

    String OPEN_TD = "<td>";
    String CLOSE_TD = "</td>";

    String OPEN_IMAGE = "<img src=../result/datafile/images/";
    String CLOSE_IMAGE = ".png /></a>";

}