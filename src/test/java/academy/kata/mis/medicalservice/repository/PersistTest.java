package academy.kata.mis.medicalservice.repository;

import academy.kata.mis.medicalservice.ContextIT;
import academy.kata.mis.medicalservice.model.entity.*;
import academy.kata.mis.medicalservice.model.enums.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PersistTest extends ContextIT {

    @Autowired
    protected AppealRepository appealRepository;
    @Autowired
    protected DepartmentRepository departmentRepository;
    @Autowired
    protected DiseaseDepRepository diseaseDepRepository;
    @Autowired
    protected DiseaseRepository diseaseRepository;
    @Autowired
    protected DiseaseSampleRepository diseaseSampleRepository;
    @Autowired
    protected DoctorRepository doctorRepository;
    @Autowired
    protected RegistrarRepository registrarRepository;
    @Autowired
    protected MedicalServiceDepRepository medicalServiceDepRepository;
    @Autowired
    protected MedicalServiceRepository medicalServiceRepository;
    @Autowired
    protected OrganizationRepository organizationRepository;
    @Autowired
    protected PatientRepository patientRepository;
    @Autowired
    protected TalonRepository talonRepository;
    @Autowired
    protected VisitRepository visitRepository;
    @Autowired
    protected XRayRepository xRayRepository;

    private Organization createOrganization(long id) {
        return organizationRepository.save(
                Organization.builder()
                        .id(id)
                        .departments(new HashSet<>())
                        .patients(new HashSet<>())
                        .build()
        );
    }

    private Department createDepartment(Organization organization, long id) {
        Department department = departmentRepository.save(
                Department.builder()
                        .id(id)
                        .doctors(new HashSet<>())
                        .diseasesDep(new HashSet<>())
                        .servicesDep(new HashSet<>())
                        .registrars(new HashSet<>())
                        .organization(organization)
                        .build()
        );
        organization.getDepartments().add(department);
        return department;
    }

    private Disease createDisease() {
        Disease disease = diseaseRepository.save(
                Disease.builder()
                        .name("name")
                        .identifier("identifier")
                        .diseasesDep(new HashSet<>())
                        .build()
        );
        disease.setIdentifier(disease.getIdentifier() + disease.getId());
        disease.setName(disease.getName() + disease.getId());
        diseaseRepository.save(disease);
        return disease;
    }

    private DiseaseDep createDiseaseDep(Department department, Disease disease) {
        DiseaseDep diseaseDep = diseaseDepRepository.save(
                DiseaseDep.builder()
                        .status(DiseaseStatus.OPEN)
                        .department(department)
                        .disease(disease)
                        .appeals(new HashSet<>())
                        .diseaseSamples(new HashSet<>())
                        .build()
        );
        department.getDiseasesDep().add(diseaseDep);
        disease.getDiseasesDep().add(diseaseDep);
        return diseaseDep;
    }

    private MedicalService createMedicalService() {
        MedicalService medicalService = medicalServiceRepository.save(
                MedicalService.builder()
                        .name("name")
                        .identifier("identifier")
                        .servicesDep(new HashSet<>())
                        .build()
        );
        medicalService.setName(medicalService.getName() + medicalService.getId());
        medicalService.setIdentifier(medicalService.getIdentifier() + medicalService.getId());
        medicalServiceRepository.save(medicalService);
        return medicalService;
    }

    private MedicalServiceDep createMedicalServiceDep(Department department,
                                                      MedicalService service) {
        MedicalServiceDep medicalServiceDep = medicalServiceDepRepository.save(
                MedicalServiceDep.builder()
                        .status(MedicalServiceStatus.OPEN)
                        .department(department)
                        .medicalService(service)
                        .visits(new HashSet<>())
                        .doctorSamples(new HashSet<>())
                        .build()
        );
        department.getServicesDep().add(medicalServiceDep);
        service.getServicesDep().add(medicalServiceDep);
        return medicalServiceDep;
    }

    private Doctor createDoctor(long personId, long positionId, String userId, Department department) {
        Doctor doctor = doctorRepository.save(
                Doctor.builder()
                        .personId(personId)
                        .positionId(positionId)
                        .userId(UUID.fromString(userId))
                        .department(department)
                        .talons(new HashSet<>())
                        .visits(new HashSet<>())
                        .diseaseSamples(new HashSet<>())
                        .build()
        );
        department.getDoctors().add(doctor);
        return doctor;
    }

    private Registrar createRegistrar(long personId, long positionId, String userId, Department department) {
        Registrar registrar = registrarRepository.save(
                Registrar.builder()
                        .personId(personId)
                        .positionId(positionId)
                        .userId(UUID.fromString(userId))
                        .department(department)
                        .build()
        );
        department.getRegistrars().add(registrar);
        return registrar;
    }

    private DiseaseSample createDiseaseSample(Doctor doctor, DiseaseDep diseaseDep, Set<MedicalServiceDep> services) {
        DiseaseSample diseaseSample = diseaseSampleRepository.save(
                DiseaseSample.builder()
                        .name("sample")
                        .description("description")
                        .doctor(doctor)
                        .diseaseDep(diseaseDep)
                        .servicesDep(services)
                        .build()
        );
        diseaseSample.setName(diseaseSample.getName() + diseaseSample.getId());
        diseaseSample.setDescription(diseaseSample.getDescription() + diseaseSample.getId());
        diseaseSampleRepository.save(diseaseSample);
        doctor.getDiseaseSamples().add(diseaseSample);
        diseaseDep.getDiseaseSamples().add(diseaseSample);
        return diseaseSample;
    }

    private Patient createPatient(long personId, String userId, Organization organization) {
        Patient patient = patientRepository.save(
                Patient.builder()
                        .personId(personId)
                        .userId(UUID.fromString(userId))
                        .talons(new HashSet<>())
                        .appeals(new HashSet<>())
                        .organization(organization)
                        .build()
        );
        organization.getPatients().add(patient);

        createAppealForPatientInOrganization(patient);

        return patient;
    }

    private void createAppealForPatientInOrganization(Patient patient) {

        List<Department> departments = new ArrayList<>(patient.getOrganization().getDepartments());

        //количество обращений
        int appealCount = ((int) (Math.random() * 10)) + 5;

        for (int i = 0; i < appealCount; i++) {
            int departmentIndex = ((int) (Math.random() * departments.size()));
            Department department = departments.get(departmentIndex);
            int diseaseDepIndex = ((int) (Math.random() * department.getDiseasesDep().size()));
            List<DiseaseDep> diseaseDeps = new ArrayList<>(department.getDiseasesDep());
            DiseaseDep diseaseDep = diseaseDeps.get(diseaseDepIndex);
            createAppeal(patient, diseaseDep);
        }
    }

    private Appeal createAppeal(Patient patient,
                                DiseaseDep diseaseDep) {
        boolean isOpen = ((int) (Math.random() * 100)) % 3 == 0;
        boolean isDMS = ((int) (Math.random() * 100)) % 2 == 0;
        int visitCount = (int) (Math.random() * 4);
        List<Integer> visits = new ArrayList<>();

        int firstVisit = (int) (Math.random() * 30) + 30;
        int lastVisit = firstVisit;
        visits.add(firstVisit);
        for (int i = 0; i < visitCount; i++) {
            int visit = firstVisit / 2;
            visits.add(visit);
            lastVisit = visit;
        }

        Appeal appeal = appealRepository.save(
                Appeal.builder()
                        .orderId(null)
                        .status(isOpen ? AppealStatus.OPEN : AppealStatus.CLOSED)
                        .insuranceType(isDMS ? InsuranceType.DMS : InsuranceType.PAY)
                        .openDate(LocalDate.now().minusDays(firstVisit))
                        .closedDate(isOpen ? null : LocalDate.now().minusDays(lastVisit))
                        .visits(new HashSet<>())
                        .xRays(new HashSet<>())
                        .patient(patient)
                        .diseaseDep(diseaseDep)
                        .build()
        );
        patient.getAppeals().add(appeal);
        diseaseDep.getAppeals().add(appeal);

        for (Integer visitDay : visits) {
            List<Doctor> doctors = new ArrayList<>(diseaseDep.getDepartment().getDoctors());
            int random = (int) (Math.random() * doctors.size());
            Doctor doctor = doctors.get(random);

            createVisit(doctor, appeal, LocalDateTime.now().minusDays(visitDay));
        }
        return appeal;
    }

    private void createVisit(Doctor doctor, Appeal appeal, LocalDateTime visitTime) {

        List<MedicalServiceDep> services = new ArrayList<>((doctor.getDepartment().getServicesDep()));

        int randomCount = ((int) (Math.random() * 10)) + 2;
        List<MedicalServiceDep> used = IntStream.range(0, randomCount)
                .mapToObj(i -> services.get((int) (Math.random() * services.size())))
                .collect(Collectors.toList());

        Visit visit = visitRepository.save(
                Visit.builder()
                        .visitTime(visitTime)
                        .doctor(doctor)
                        .appeal(appeal)
                        .medicalServicesDep(used)
                        .build()
        );
        appeal.getVisits().add(visit);
        doctor.getVisits().add(visit);
        used.forEach(s -> s.getVisits().add(visit));
        medicalServiceDepRepository.saveAll(used);

        if (randomCount % 2 == 0) {
            XRay xRay1 = xRayRepository.save(
                    XRay.builder()
                            .time(visitTime.minusHours(1))
                            .type(DocumentType.JPEG)
                            .documentId(UUID.randomUUID())
                            .size(10240L)
                            .hash(UUID.randomUUID().toString())
                            .appeal(appeal)
                            .build()
            );
            appeal.getXRays().add(xRay1);
        }
    }

    private List<LocalTime> timesForDay(LocalDate date, WorkSchedule workSchedule) {
        boolean i = date.getDayOfMonth() / 2 == 0;
        //если true - день четный
        if (i) {
            //день четный
            if (workSchedule == WorkSchedule.ODD) {
                //если у работника первая смена по нечетным дням
                return WorkingTimeZone.EVENING.getTimes();
            }
            if (workSchedule == WorkSchedule.EVEN) {
                //если у работника первая смена по четным дням
                return WorkingTimeZone.MORNING.getTimes();
            }
        } else {
            //день не четный
            if (workSchedule == WorkSchedule.ODD) {
                //если у работника первая смена по нечетным дням
                return WorkingTimeZone.MORNING.getTimes();
            }
            if (workSchedule == WorkSchedule.EVEN) {
                //если у работника первая смена по четным дням
                return WorkingTimeZone.EVENING.getTimes();
            }
        }
        return WorkingTimeZone.DAY.getTimes();
    }

    private void createTalonsForDoctor(Doctor doctor, WorkSchedule workSchedule, int days) {
        Set<Integer> workingDays = Set.of(1, 2, 3, 4, 5);
        LocalDate dayStart = LocalDate.now().plusDays(1);

        for (int i = 0; i < days; i++) {
            LocalDate currentDay = dayStart.plusDays(i);
            List<LocalTime> timesForDay = timesForDay(currentDay, workSchedule);

            if (workingDays.contains(currentDay.getDayOfWeek().getValue())) {
                for (LocalTime talonTime : timesForDay) {
                    Talon talon = talonRepository.save(
                            Talon.builder()
                                    .time(LocalDateTime.of(currentDay, talonTime))
                                    .patient(null)
                                    .doctor(doctor)
                                    .build()
                    );
                    doctor.getTalons().add(talon);
                }
            }
        }

        List<Talon> talons = new ArrayList<>(doctor.getTalons());
        List<Patient> patients = new ArrayList<>(doctor.getDepartment().getOrganization().getPatients());
        int count = talons.size() * 25 / 100;

        for (int i = 0; i < count; i++) {
            int randomTalon = (int) (Math.random() * talons.size());
            int randomPatient = (int) (Math.random() * patients.size());

            Talon talon = talons.get(randomTalon);
            if (talon.getPatient() != null) {
                --i;
                continue;
            }
            Patient patient = patients.get(randomPatient);
            talon.setPatient(patient);
            talonRepository.save(talon);
            patient.getTalons().add(talon);
        }
    }

    @Disabled
    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/clear.sql")
    public void persistTest() {

        //справочник из 10 заболеваний
        Disease disease1 = createDisease();
        Disease disease2 = createDisease();
        Disease disease3 = createDisease();
        Disease disease4 = createDisease();
        Disease disease5 = createDisease();
        Disease disease6 = createDisease();
        Disease disease7 = createDisease();
        Disease disease8 = createDisease();
        Disease disease9 = createDisease();
        Disease disease10 = createDisease();

        //справочник из 10 медицинских услуг
        MedicalService medicalService1 = createMedicalService();
        MedicalService medicalService2 = createMedicalService();
        MedicalService medicalService3 = createMedicalService();
        MedicalService medicalService4 = createMedicalService();
        MedicalService medicalService5 = createMedicalService();
        MedicalService medicalService6 = createMedicalService();
        MedicalService medicalService7 = createMedicalService();
        MedicalService medicalService8 = createMedicalService();
        MedicalService medicalService9 = createMedicalService();
        MedicalService medicalService10 = createMedicalService();

        //сохранил организацию 1
        Organization organization1 = createOrganization(1L);

        //сохранил 2 отделения в организацию 1
        Department department1 = createDepartment(organization1, 1L);
        Department department2 = createDepartment(organization1, 2L);

        //справочник заболеваний для 1 отделения
        DiseaseDep diseaseDep1_1 = createDiseaseDep(department1, disease1);
        DiseaseDep diseaseDep1_2 = createDiseaseDep(department1, disease2);
        DiseaseDep diseaseDep1_3 = createDiseaseDep(department1, disease3);
        DiseaseDep diseaseDep1_4 = createDiseaseDep(department1, disease4);
        DiseaseDep diseaseDep1_5 = createDiseaseDep(department1, disease5);
        DiseaseDep diseaseDep1_6 = createDiseaseDep(department1, disease6);
        DiseaseDep diseaseDep1_7 = createDiseaseDep(department1, disease7);

        //справочник заболеваний для 2 отделения
        DiseaseDep diseaseDep2_6 = createDiseaseDep(department2, disease6);
        DiseaseDep diseaseDep2_7 = createDiseaseDep(department2, disease7);
        DiseaseDep diseaseDep2_8 = createDiseaseDep(department2, disease8);
        DiseaseDep diseaseDep2_9 = createDiseaseDep(department2, disease9);
        DiseaseDep diseaseDep2_10 = createDiseaseDep(department2, disease10);

        //справочник мед услуг для 1 отделения
        MedicalServiceDep medicalServiceDep1_1 = createMedicalServiceDep(department1, medicalService1);
        MedicalServiceDep medicalServiceDep1_2 = createMedicalServiceDep(department1, medicalService2);
        MedicalServiceDep medicalServiceDep1_3 = createMedicalServiceDep(department1, medicalService3);
        MedicalServiceDep medicalServiceDep1_4 = createMedicalServiceDep(department1, medicalService4);
        MedicalServiceDep medicalServiceDep1_5 = createMedicalServiceDep(department1, medicalService5);
        MedicalServiceDep medicalServiceDep1_6 = createMedicalServiceDep(department1, medicalService6);
        MedicalServiceDep medicalServiceDep1_7 = createMedicalServiceDep(department1, medicalService7);

        //справочник мед услуг для 2 отделения
        MedicalServiceDep medicalServiceDep2_6 = createMedicalServiceDep(department2, medicalService6);
        MedicalServiceDep medicalServiceDep2_7 = createMedicalServiceDep(department2, medicalService7);
        MedicalServiceDep medicalServiceDep2_8 = createMedicalServiceDep(department2, medicalService8);
        MedicalServiceDep medicalServiceDep2_9 = createMedicalServiceDep(department2, medicalService9);
        MedicalServiceDep medicalServiceDep2_10 = createMedicalServiceDep(department2, medicalService10);

        //докторы для 1 отдления
        Doctor doctor1 = createDoctor(1L, 1L, "ac9360fd-75ba-46c1-81dd-b9f54962aca5", department1);
        Doctor doctor2 = createDoctor(2L, 2L, "5708767e-f51f-4dac-a4c9-828446473aa4", department1);
        Doctor doctor3 = createDoctor(3L, 3L, "31c2cd49-939a-4227-ae8e-c95b0a4456b6", department1);
        Doctor doctor4 = createDoctor(4L, 4L, "04fe2814-0b70-40d5-8af4-a4f9a6d7dd3e", department1);
        Registrar registrar1 = createRegistrar(7L, 7L, "1ec65427-797c-48a5-9a2c-82ced26053c5", department1);

        //создаю шаблоны для докторов 1 отделения
        DiseaseSample diseaseSample1 =
                createDiseaseSample(doctor1, diseaseDep1_1, Set.of(medicalServiceDep1_1, medicalServiceDep1_2));
        DiseaseSample diseaseSample2 =
                createDiseaseSample(doctor1, diseaseDep1_2, Set.of(medicalServiceDep1_3, medicalServiceDep1_4));
        DiseaseSample diseaseSample3 =
                createDiseaseSample(doctor1, diseaseDep1_2, Set.of(medicalServiceDep1_3, medicalServiceDep1_4));
        DiseaseSample diseaseSample4 =
                createDiseaseSample(doctor2, diseaseDep1_1, Set.of(medicalServiceDep1_6, medicalServiceDep1_2));
        DiseaseSample diseaseSample5 =
                createDiseaseSample(doctor2, diseaseDep1_4, Set.of(medicalServiceDep1_5, medicalServiceDep1_4));
        DiseaseSample diseaseSample6 =
                createDiseaseSample(doctor2, diseaseDep1_5, Set.of(medicalServiceDep1_3, medicalServiceDep1_7));
        DiseaseSample diseaseSample7 =
                createDiseaseSample(doctor3, diseaseDep1_1, Set.of(medicalServiceDep1_1, medicalServiceDep1_2));
        DiseaseSample diseaseSample8 =
                createDiseaseSample(doctor3, diseaseDep1_2, Set.of(medicalServiceDep1_3, medicalServiceDep1_4));
        DiseaseSample diseaseSample9 =
                createDiseaseSample(doctor3, diseaseDep1_2, Set.of(medicalServiceDep1_3, medicalServiceDep1_4));
        DiseaseSample diseaseSample10 =
                createDiseaseSample(doctor4, diseaseDep1_1, Set.of(medicalServiceDep1_6, medicalServiceDep1_2));
        DiseaseSample diseaseSample11 =
                createDiseaseSample(doctor4, diseaseDep1_4, Set.of(medicalServiceDep1_5, medicalServiceDep1_4));
        DiseaseSample diseaseSample12 =
                createDiseaseSample(doctor4, diseaseDep1_5, Set.of(medicalServiceDep1_3, medicalServiceDep1_7));

        //докторы для 2 отдления
        Doctor doctor5 = createDoctor(10L, 10L, "4b32f6f2-add4-4700-b193-6d502105677b", department2);
        Doctor doctor6 = createDoctor(11L, 11L, "a06d611e-81bc-4321-9e65-521205e199a3", department2);

        Registrar registrar2 = createRegistrar(13L, 13L, "3504477c-51a9-4631-b9c7-5cd43e45f2e7", department2);

        //создаю шаблоны для докторов 2 отделения
        DiseaseSample diseaseSample13 =
                createDiseaseSample(doctor5, diseaseDep2_6, Set.of(medicalServiceDep2_6, medicalServiceDep2_7));
        DiseaseSample diseaseSample14 =
                createDiseaseSample(doctor5, diseaseDep2_7, Set.of(medicalServiceDep2_7, medicalServiceDep2_8));
        DiseaseSample diseaseSample15 =
                createDiseaseSample(doctor5, diseaseDep2_8, Set.of(medicalServiceDep2_8, medicalServiceDep2_9));
        DiseaseSample diseaseSample16 =
                createDiseaseSample(doctor6, diseaseDep2_9, Set.of(medicalServiceDep2_9, medicalServiceDep2_10));
        DiseaseSample diseaseSample17 =
                createDiseaseSample(doctor6, diseaseDep2_10, Set.of(medicalServiceDep2_10, medicalServiceDep2_6));
        DiseaseSample diseaseSample18 =
                createDiseaseSample(doctor6, diseaseDep2_6, Set.of(medicalServiceDep2_6, medicalServiceDep2_7));

        //создаю пациентов в организацию 1 и обращения
        Patient patient1 = createPatient(27L, "0929d857-e380-4f73-b020-fa253772d079", organization1);
        Patient patient2 = createPatient(28L, "662b6f6e-4702-44c4-98f4-e73243087d46", organization1);
        Patient patient3 = createPatient(29L, "1a4eb80c-56a1-4434-97e2-ba11df07b3ef", organization1);
        Patient patient4 = createPatient(30L, "599d9ef0-7ae0-4924-890b-55eb13f85e53", organization1);
        Patient patient5 = createPatient(31L, "61a3117a-ff95-4359-9a8e-d4af05721609", organization1);

        Patient patient6 = createPatient(32L, "9ed7ffb6-2a34-4d8e-84db-4c0370a2cd5a", organization1);
        Patient patient7 = createPatient(33L, "9bebf885-9c70-4b25-826a-a238e1e4519e", organization1);
        Patient patient8 = createPatient(34L, "3a81fbc8-bbde-4fcc-b026-da3427ebac21", organization1);
        Patient patient9 = createPatient(35L, "67358b95-0eb1-46fa-874b-7b346d831041", organization1);
        Patient patient10 = createPatient(36L, "ba44197f-3268-46ff-8b60-0e5d8596b6dd", organization1);

        Patient patient11 = createPatient(37L, "3c0f6e56-69f3-459e-9fe8-5069d4537951", organization1);
        Patient patient12 = createPatient(38L, "faa6711a-8e44-4685-b72d-2ceedb221b67", organization1);
        Patient patient13 = createPatient(39L, "501ae805-4fdc-4e60-9a3f-967bd3c7fe9a", organization1);
        Patient patient14 = createPatient(40L, "6a47bbb9-6c24-4c28-a2e8-26939c0e6505", organization1);
        Patient patient15 = createPatient(41L, "179ab015-5622-45f3-aae2-bd4b31ff60c0", organization1);

        Patient patient16 = createPatient(42L, "83bb225a-ad24-474f-a6e8-64a26c7db63f", organization1);
        Patient patient17 = createPatient(43L, "1ce6590e-9dcc-4a58-afea-b1fb58d9342b", organization1);
        Patient patient18 = createPatient(44L, "99efc3cd-c5e6-4469-ab40-ff97d3d98882", organization1);
        Patient patient19 = createPatient(45L, "cc70ff03-5637-46da-9dc4-91ccbd8c51c3", organization1);
        Patient patient20 = createPatient(46L, "cb62f28d-789c-466b-ba1d-9fe7558218ef", organization1);

        Patient patient21 = createPatient(47L, "245422b5-dfdd-497b-89eb-3b44af6a0d77", organization1);
        Patient patient22 = createPatient(48L, "2fb3422d-ae0c-4419-8418-339abf8b5181", organization1);
        Patient patient23 = createPatient(49L, "8056af97-faf4-4c81-af69-81259de5034a", organization1);
        Patient patient24 = createPatient(50L, "def6ad34-fb99-4a28-bafe-29287dd5e8eb", organization1);
        Patient patient25 = createPatient(51L, "ed9409fc-965d-4c8d-8ab0-b24d33798107", organization1);

        Patient patient26 = createPatient(52L, "897e72d4-dbb6-416f-90d7-023e1bd8309c", organization1);
        Patient patient27 = createPatient(53L, "fd1e4377-cbcb-4bb5-a201-8c6a4d6040d2", organization1);
        Patient patient28 = createPatient(54L, "d9315b35-720b-4583-b164-c3cb08dcf6b9", organization1);
        Patient patient29 = createPatient(55L, "442875c2-9803-4e82-aa2c-80e6d839790d", organization1);
        Patient patient30 = createPatient(56L, "e6295437-ea1d-4d37-8e8b-00a04a62cf8d", organization1);


        //создаю талоны докторам 1 организации
        createTalonsForDoctor(doctor1, WorkSchedule.STABLE, 14);
        createTalonsForDoctor(doctor2, WorkSchedule.EVEN, 14);
        createTalonsForDoctor(doctor3, WorkSchedule.EVEN, 14);
        createTalonsForDoctor(doctor4, WorkSchedule.EVEN, 14);
        createTalonsForDoctor(doctor5, WorkSchedule.ODD, 14);
        createTalonsForDoctor(doctor6, WorkSchedule.ODD, 14);

        //создаю 3 отделение для прочего персонала в 1 организацию
        Department department3 = createDepartment(organization1, 3L);

        //сохранил организацию 2
        Organization organization2 = createOrganization(2L);

        //сохранил 2 отделения в организацию 2
        Department department4 = createDepartment(organization2, 4L);
        Department department5 = createDepartment(organization2, 5L);

        //докторы для 2 организации
        Doctor doctor7 = createDoctor(18L, 18L, "0759545c-1d8c-4513-9f70-c8de0dc97c7f", department4);
        Doctor doctor8 = createDoctor(19L, 19L, "0e8e16b9-a474-41f6-9d78-0fe41e6aa564", department4);
        Doctor doctor9 = createDoctor(20L, 20L, "63fcae3f-ae3c-48e8-b073-b91a2af624b5", department4);
        Registrar registrar3 = createRegistrar(22L, 22L, "11a3767e-7c45-4851-ad63-3d66a3f55796", department4);

        System.out.println("debug mode");
    }
}
