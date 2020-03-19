package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomAttendanceCode;
import com.wisdom.classroom.entity.WisdomAttendanceRecord;
import com.wisdom.classroom.vo.AttendanceRecordVO;

import java.util.List;


public interface WisdomAttendanceRecordService extends IService<WisdomAttendanceRecord> {

    Integer batchInitRecord(Long codeId);

    List<AttendanceRecordVO> selectAttendanceRecord(Long codeId);

    List<AttendanceRecordVO> selectAttendanceRecordByUserId(Long userId, String attendanceTime);

}