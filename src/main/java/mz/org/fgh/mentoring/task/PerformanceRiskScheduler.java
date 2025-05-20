package mz.org.fgh.mentoring.task;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import mz.org.fgh.mentoring.service.session.SessionService;
import mz.org.fgh.mentoring.service.setting.SettingService;
import mz.org.fgh.mentoring.entity.setting.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Singleton
public class PerformanceRiskScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(PerformanceRiskScheduler.class);

    private final SessionService sessionService;
    private final SettingService settingService;

    public PerformanceRiskScheduler(SessionService sessionService, SettingService settingService) {
        this.sessionService = sessionService;
        this.settingService = settingService;
    }

    //@Scheduled(cron = "0 0 3 * * *") // Executa diariamente às 03:00
    @Scheduled(fixedDelay = "10m")
    void runRiskEvaluation() {
        Optional<Setting> settingOpt = settingService.getSettingByDeignation("AI_PERFORMANCE_RISK_EVALUATION");

        if (settingOpt.isPresent() && settingOpt.get().getEnabled()) {
            LOG.info("Iniciando avaliação de risco de desempenho com IA...");
            sessionService.evaluatePerformanceRiskPerRonda();
        } else {
            LOG.info("Avaliação de risco de desempenho está desativada via configuração.");
        }
    }
}
