package com.top.deploy.membebatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleBatchApplication {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;

	@Bean
	public Job job() {
		return new JobBuilder("simpleJob", jobRepository)
				.start(step())
				.build();
	}

	@Bean
	public Step step() {
		return new StepBuilder("simpleStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					log.info("심플 배치 실행됨");
					return RepeatStatus.FINISHED;
				}, transactionManager)
				.build();
	}
}
