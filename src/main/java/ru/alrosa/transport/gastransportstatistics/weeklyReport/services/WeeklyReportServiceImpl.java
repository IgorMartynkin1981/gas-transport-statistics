package ru.alrosa.transport.gastransportstatistics.weeklyReport.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.exception.ValidationDataException;
import ru.alrosa.transport.gastransportstatistics.serializationdeserialization.UtilClass;
import ru.alrosa.transport.gastransportstatistics.subdivisions.model.Subdivision;
import ru.alrosa.transport.gastransportstatistics.subdivisions.repositories.SubdivisionRepository;
import ru.alrosa.transport.gastransportstatistics.users.model.User;
import ru.alrosa.transport.gastransportstatistics.users.repositories.UserRepository;
import ru.alrosa.transport.gastransportstatistics.weeklyReport.dto.InfoWeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.weeklyReport.dto.WeeklyReportDto;
import ru.alrosa.transport.gastransportstatistics.weeklyReport.dto.WeeklyReportMapper;
import ru.alrosa.transport.gastransportstatistics.weeklyReport.model.WeeklyReport;
import ru.alrosa.transport.gastransportstatistics.weeklyReport.reposytories.WeeklyReportRepositories;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class WeeklyReportServiceImpl implements WeeklyReportService {

    private final WeeklyReportRepositories weeklyReportRepositories;
    private final UserRepository userRepository;
    private final SubdivisionRepository subdivisionRepository;

    @Autowired
    public WeeklyReportServiceImpl(WeeklyReportRepositories weeklyReportRepositories,
                                   UserRepository userRepository,
                                   SubdivisionRepository subdivisionRepository) {
        this.weeklyReportRepositories = weeklyReportRepositories;
        this.userRepository = userRepository;
        this.subdivisionRepository = subdivisionRepository;
    }

    @Override
    public Collection<InfoWeeklyReportDto> getAllWeeklyReport(Long subdivisionId, String periodStart, String periodEnd) {
        if (subdivisionId != 0) {
            return weeklyReportRepositories.findAllByPeriodStartAfterAndPeriodEndBefore(subdivisionId, UtilClass.toLocalDateTime(periodStart),
                            UtilClass.toLocalDateTime(periodEnd))
                    .stream()
                    .map(WeeklyReportMapper::toInfoWeeklyReportDto)
                    .collect(Collectors.toList());
        } else {
            return weeklyReportRepositories.findAllByPeriodStartAfterAndPeriodEndBefore(UtilClass.toLocalDateTime(periodStart),
                            UtilClass.toLocalDateTime(periodEnd))
                    .stream()
                    .map(WeeklyReportMapper::toInfoWeeklyReportDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public InfoWeeklyReportDto getWeeklyReportById(Long weeklyReportId) {
        return WeeklyReportMapper.toInfoWeeklyReportDto(findAndVerifyWeeklyReportInRepository(weeklyReportId));
    }

    @Override
    public InfoWeeklyReportDto updateWeeklyReport(WeeklyReportDto weeklyReportDto) {
        if (weeklyReportDto.getId() == null || weeklyReportDto.getId() <= 0) {
            throw new ValidationDataException("WeeklyReportId = null or Sub 0, it's wrong!");
        }
        return saveWeeklyReport(weeklyReportDto);
    }

    @Override
    public InfoWeeklyReportDto createWeeklyReport(WeeklyReportDto weeklyReportDto) {
        return saveWeeklyReport(weeklyReportDto);
    }

    @Override
    public void deleteWeeklyReportById(Long weeklyReportId) {
        weeklyReportRepositories.deleteById(weeklyReportId);
    }

    private InfoWeeklyReportDto saveWeeklyReport(WeeklyReportDto weeklyReportDto) {
        if (weeklyReportDto.getUserId() == null || weeklyReportDto.getUserId() <= 0 ||
                weeklyReportDto.getSubdivisionId() == null || weeklyReportDto.getSubdivisionId() <= 0) {
            throw new ValidationDataException("UserId or SubdivisionId = null, it's wrong!");
        }
        User user = userRepository.findById(weeklyReportDto.getUserId()).orElseThrow(() -> new DataNotFound(
                String.format("User with id %d was not found in the database", weeklyReportDto.getUserId())));
        Subdivision subdivision = subdivisionRepository
                .findById(weeklyReportDto.getSubdivisionId())
                .orElseThrow(() -> new DataNotFound(
                        String.format(
                                "User with id %d was not found in the database", weeklyReportDto.getSubdivisionId()
                        )
                ));
        return WeeklyReportMapper.toInfoWeeklyReportDto(
                weeklyReportRepositories.save(WeeklyReportMapper.toWeeklyReport(weeklyReportDto, user, subdivision))
        );
    }

    private WeeklyReport findAndVerifyWeeklyReportInRepository(Long weeklyReportId) {
        return weeklyReportRepositories.findById(weeklyReportId).orElseThrow(() -> new DataNotFound(
                String.format("WeeklyReport with id %d was not found in the database", weeklyReportId)));
    }
}
