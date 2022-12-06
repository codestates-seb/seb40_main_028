package com.seb028.guenlog.exercise.mapper;

import com.seb028.guenlog.exercise.dto.ExercisePlanResponseDto;
import com.seb028.guenlog.exercise.entity.Record;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExercisePlanMapper {


    default ExercisePlanResponseDto.RecordPostResponseDto recordToRecordPostResponseDto(Record record){
        ExercisePlanResponseDto.RecordPostResponseDto postDto = new ExercisePlanResponseDto.RecordPostResponseDto();
        postDto.setRecordId(record.getId());
        postDto.setExerciseId(record.getExercise().getId());
        postDto.setName(record.getExercise().getName());
        postDto.setRecords(record.getEachRecords());
        return postDto;
    }

    default List<ExercisePlanResponseDto.RecordBaseResponseDto> recordToRecordBaseResponseDto(List<Record> records){
        return records.stream().map(record -> {
            ExercisePlanResponseDto.RecordBaseResponseDto recordTodoDto = new ExercisePlanResponseDto.RecordBaseResponseDto();
            recordTodoDto.setRecordId(record.getId());
            recordTodoDto.setName(record.getExercise().getName());
            return recordTodoDto;
        }).collect(Collectors.toList());
    }

    default ExercisePlanResponseDto.RecordDetailResponseDto recordToRecordDetailNameResponseDto(Record records){
        ExercisePlanResponseDto.RecordDetailResponseDto recordDto = new ExercisePlanResponseDto.RecordDetailResponseDto();
        recordDto.setExerciseId(records.getExercise().getId());
        recordDto.setName(records.getExercise().getName());
        recordDto.setImages(records.getExercise().getImageUrl());
        recordDto.setRecords(records.getEachRecords());
        return recordDto;
    }

}
