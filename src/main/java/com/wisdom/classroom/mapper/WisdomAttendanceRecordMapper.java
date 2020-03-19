package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomAttendanceRecord;

import com.wisdom.classroom.util.MyMapper;
import com.wisdom.classroom.vo.AttendanceRecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WisdomAttendanceRecordMapper extends MyMapper<WisdomAttendanceRecord> {

    Integer batchInitRecord(Long codeId);

    List<AttendanceRecordVO> selectAttendanceRecord(Long codeId);

    List<AttendanceRecordVO> selectAttendanceRecordByUserId(@Param("userId") Long userId, @Param("attendanceTime") String attendanceTime);

}