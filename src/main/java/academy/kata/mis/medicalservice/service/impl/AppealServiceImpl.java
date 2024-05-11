package academy.kata.mis.medicalservice.service.impl;

import academy.kata.mis.medicalservice.model.entity.Appeal;
import academy.kata.mis.medicalservice.repository.AppealRepository;
import academy.kata.mis.medicalservice.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private final AppealRepository appealRepository;

    @Override
    public Appeal getAppealById(long appealId) {
        return appealRepository.getAppealById(appealId);
    }
}
