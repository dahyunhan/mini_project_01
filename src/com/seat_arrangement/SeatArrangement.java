package com.seat_arrangement;

import com.seat_arrangement.controller.SeatArrangementController;

public class SeatArrangement {

    static SeatArrangementController controller = new SeatArrangementController();

    public static void main(String[] args) {
        // 프로그램 실행
        controller.run();
    }
}