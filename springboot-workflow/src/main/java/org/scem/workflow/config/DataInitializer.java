package org.scem.workflow.config;

import org.scem.workflow.entity.*;
import org.scem.workflow.enumeration.SpecificUserLogin;
import org.scem.workflow.enumeration.StepType;
import org.scem.workflow.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    @Transactional
    public ApplicationRunner initData(WorkflowDefinitionRepository workflowDefinitionRepository,
                                      StepDefinitionRepository stepDefinitionRepository) {
        return args -> {

            int workflowDefinitionRepositoryCount = (int) workflowDefinitionRepository.count();
            logger.info("Number of records in WorkflowDefinition : {}", workflowDefinitionRepositoryCount);

            if (workflowDefinitionRepositoryCount == 0) {
                // Création du workflow
                WorkflowDefinition workflow = new WorkflowDefinition();
                workflow.setName("Demande RH");
                workflow.setDescription("Workflow pour la gestion des demandes des ressources humaines");
                workflow = workflowDefinitionRepository.save(workflow);

                // Création des étapes
                StepDefinition soumission = new StepDefinition();
                soumission.setWorkflowDefinition(workflow);
                soumission.setName("Soumission");
                soumission.setDescription("Soumission de la demande par l'employé");
                soumission.setType(StepType.USER);
                soumission.setGroups(List.of("*"));
                soumission.setStart(true);
                soumission = stepDefinitionRepository.save(soumission);

                StepDefinition validationManager = new StepDefinition();
                validationManager.setWorkflowDefinition(workflow);
                validationManager.setName("Validation Manager");
                validationManager.setDescription("Validation de la demande par le manager");
                validationManager.setType(StepType.USER);
                validationManager.setGroups(List.of("*"));
                validationManager = stepDefinitionRepository.save(validationManager);

                StepDefinition validationRH = new StepDefinition();
                validationRH.setWorkflowDefinition(workflow);
                validationRH.setName("Validation RH");
                validationRH.setDescription("Validation finale par le département RH");
                validationRH.setType(StepType.USER);
                validationRH.setGroups(List.of("*"));
                validationRH = stepDefinitionRepository.save(validationRH);

                StepDefinition cloture = new StepDefinition();
                cloture.setWorkflowDefinition(workflow);
                cloture.setName("Clôture");
                cloture.setDescription("Clôture de la demande");
                cloture.setType(StepType.USER);
                cloture.setLogins(List.of(SpecificUserLogin.JAFLOW_WORKFLOW_CREATOR.name()));
                cloture = stepDefinitionRepository.save(cloture);

                StepDefinition refus = new StepDefinition();
                refus.setWorkflowDefinition(workflow);
                refus.setName("Refus");
                refus.setDescription("Refus de la demande");
                refus.setType(StepType.USER);
                refus.setLogins(List.of(SpecificUserLogin.JAFLOW_WORKFLOW_CREATOR.name()));
                refus = stepDefinitionRepository.save(refus);

                soumission.setTransitions(Map.of(
                        "Valider",validationManager,
                        "Refuser",refus
                ));
                validationManager.setTransitions(Map.of(
                        "Valider",validationRH,
                        "Refuser",refus
                ));
                validationRH.setTransitions(Map.of(
                        "Valider",cloture,
                        "Refuser",refus
                ));

                stepDefinitionRepository.save(soumission);
                stepDefinitionRepository.save(validationManager);
                stepDefinitionRepository.save(validationRH);
            }
        };
    }
}
