package com.seb028.guenlog.exercise.mapper;

import com.seb028.guenlog.exercise.dto.ExerciseMainResponseDto;
import com.seb028.guenlog.exercise.entity.Record;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ExerciseMainMapper {

    default List<ExerciseMainResponseDto.ExerciseDto> recordToExerciseDto(List<Record> records){
        return records.stream().map(record -> {
            ExerciseMainResponseDto.ExerciseDto exerciseDto = new ExerciseMainResponseDto.ExerciseDto();
            exerciseDto.setExerciseId(record.getExercise().getId());
            exerciseDto.setExerciseName(record.getExercise().getName());
            exerciseDto.setIsComleted(record.getIsCompleted());
            return exerciseDto;
        }).collect(Collectors.toList());
    }

    default ExerciseMainResponseDto.RecordDetailDto exercisesDtoToRecordDetailDto(List<ExerciseMainResponseDto.ExerciseDto> dto, Integer timer ) {
        ExerciseMainResponseDto.RecordDetailDto recordDetailDto = new ExerciseMainResponseDto.RecordDetailDto();
        recordDetailDto.setTotalTime(timer);
        recordDetailDto.setExercises(dto);
        return recordDetailDto;
    }
}
