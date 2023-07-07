package com.seat_arrangement.controller;


import com.seat_arrangement.repository.*;
import com.seat_arrangement.repository.dbconnect.DBConnection;
import com.seat_arrangement.repository.impl_auto_increment.ArrangementRepoImpl;
import com.seat_arrangement.repository.impl_auto_increment.SeatRepoImpl;
import com.seat_arrangement.repository.impl_auto_increment.StudentRepoImpl;
import com.seat_arrangement.service.RandomArrangementService;
import com.seat_arrangement.util.upload.*;
import com.seat_arrangement.view.HTMLMaker;

import java.util.ArrayList;

import static com.seat_arrangement.util.ArrangeInfo.TODAY;

public class SeatArrangementController {

    private final RandomArrangementService service = new RandomArrangementService();

    private final ArrangementRepoImpl arrangementRepo = new ArrangementRepoImpl();
    private final StudentRepoImpl studentRepo = new StudentRepoImpl();
    private final SeatRepoImpl seatRepo = new SeatRepoImpl();

    private ArrayList<Integer> arrangedStudents;
    private ArrayList<Integer> arrangedSeats;
    public ArrayList<Integer> sortedStudentIds; // HTML 배치에 쓰일 실제 36좌석 배치 List

    //전체 코드 실행
    public void run() {
        DBConnection.get();
        initInfo();
        arrange();
        createHTML();
    }

    // 학생, 자리 정보 초기화
    private void initInfo() {
        DefaultUploader.loadInfo();

        StudentDefaultModifier.modifyInfo();
        SeatDefaultModifier.modifyInfo();
    }

    // 배치하기
    private void arrange() {
        this.arrangedStudents = service.arrangeStudent(studentRepo.findAllUsedId());
        this.arrangedSeats = service.arrangeSeat(seatRepo.findAllUsedId());
        this.setUnused();

        for (int idx = 0; idx < arrangedStudents.size(); idx++) {
            arrangementRepo.save(this.arrangedSeats.get(idx), this.arrangedStudents.get(idx));
        }
        this.sortedStudentIds = service.sortBySeat(arrangementRepo.findByDate(TODAY));
        System.out.println(sortedStudentIds.size());
    }

    // 결원과 사용하지 않는 자리 매핑해 총 결과값으로 변경 (모든 배치 방법에 필수)
    // 결원 id = 0 취급
    private void setUnused() {
        ArrayList<Integer> unusedSeats = seatRepo.findAllNotUsedId();
        for (int i = 0; i < unusedSeats.size(); i++) {
            this.arrangedStudents.add(0);
        }
        this.arrangedSeats.addAll(unusedSeats);
    }


    private void createHTML() {
        HTMLMaker.make(sortedStudentIds);
        HTMLMaker.save();
    }
}
