package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomAttendanceCode;
import com.wisdom.classroom.entity.WisdomAttendanceRecord;
import com.wisdom.classroom.mapper.WisdomAttendanceRecordMapper;
import com.wisdom.classroom.service.WisdomAttendanceCodeService;
import com.wisdom.classroom.service.WisdomAttendanceRecordService;
import com.wisdom.classroom.vo.AttendanceRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WisdomAttendanceRecordServiceImpl extends BaseService<WisdomAttendanceRecord> implements WisdomAttendanceRecordService {

    @Autowired
    private WisdomAttendanceRecordMapper mapper;

    @Override
    public Integer batchInitRecord(Long codeId) {
        return mapper.batchInitRecord(codeId);
    }

    @Override
    public List<AttendanceRecordVO> selectAttendanceRecord(Long codeId) {
        return mapper.selectAttendanceRecord(codeId);
    }

    @Override
    public List<AttendanceRecordVO> selectAttendanceRecordByUserId(Long userId, String attendanceTime) {
        return mapper.selectAttendanceRecordByUserId(userId, attendanceTime);
    }
}
