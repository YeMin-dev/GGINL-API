package org.tat.gginl.api.domains.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tat.gginl.api.common.COACode;
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.DateUtils;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.TLFBuilder;
import org.tat.gginl.api.common.TranCode;
import org.tat.gginl.api.common.Utils;
import org.tat.gginl.api.common.emumdata.AgentCommissionEntryType;
import org.tat.gginl.api.common.emumdata.DoubleEntry;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.common.emumdata.PolicyStatus;
import org.tat.gginl.api.common.emumdata.ProposalType;
import org.tat.gginl.api.common.emumdata.Status;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.AgentCommission;
import org.tat.gginl.api.domains.Bank;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.MedicalPolicy;
import org.tat.gginl.api.domains.MedicalPolicyRepository;
import org.tat.gginl.api.domains.MedicalProposal;
import org.tat.gginl.api.domains.MedicalProposalInsuredPerson;
import org.tat.gginl.api.domains.MedicalProposalInsuredPersonBeneficiaries;
import org.tat.gginl.api.domains.MedicalProposalRepository;
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.PaymentType;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.domains.RelationShip;
import org.tat.gginl.api.domains.SaleMan;
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.TLF;
import org.tat.gginl.api.domains.Township;
import org.tat.gginl.api.domains.repository.AgentCommissionRepository;
import org.tat.gginl.api.domains.repository.CustomerRepository;
import org.tat.gginl.api.domains.repository.PaymentRepository;
import org.tat.gginl.api.domains.repository.TLFRepository;
import org.tat.gginl.api.dto.healthdto.HealthInsuredPersonBeneficiaryDTO;
import org.tat.gginl.api.dto.healthdto.HealthProposalDTO;
import org.tat.gginl.api.dto.healthdto.HealthProposalInsuredPersonDTO;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.SystemException;

@Service
@PropertySource("classpath:keyfactor-id-config.properties")
public class MedicalProposalService {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private MedicalProposalRepository medicalProposalRepo;

  @Autowired
  private MedicalPolicyRepository medicalPolicyRepo;

  @Autowired
  private BranchService branchService;

  @Autowired
  private CustomerRepository customerRepo;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private OrganizationService organizationService;

  @Autowired
  private PaymentTypeService paymentTypeService;

  @Autowired
  private AgentService agentService;

  @Autowired
  private SaleManService saleManService;

  @Autowired
  private SalePointService salePointService;

  @Autowired
  private ProductService productService;

  @Autowired
  private TownShipService townShipService;

  @Autowired
  private OccupationService occupationService;

  @Autowired
  private RelationshipService relationshipService;

  @Autowired
  private ICustomIdGenerator customIdRepo;

  @Autowired
  private PaymentRepository paymentRepository;

  @Autowired
  private TLFRepository tlfRepository;

  @Autowired
  private AgentCommissionRepository agentCommissionRepo;

  @Autowired
  private BankService bankService;

  @Autowired
  private GradeInfoService gradeInfoService;

  @Autowired
  private SchoolService schoolService;

  @Autowired
  private StateCodeService stateCodeService;

  @Autowired
  private TownShipCodeService townShipCodeService;

  @Autowired
  private CountryService countryService;

  @Value("${healthProductId}")
  private String healthProductId;


  // for health proposalto policy
  @Transactional(propagation = Propagation.REQUIRED)
  public List<MedicalPolicy> createHealthProposalToPolicy(HealthProposalDTO healthProposalDTO) {
    try {
      List<MedicalProposal> healthProposalList =
          convertHealthProposalDTOToProposal(healthProposalDTO);

      Date paymentConfirmDate = healthProposalDTO.getPaymentConfirmDate();

      // convert healthproposal to lifepolicy
      List<MedicalPolicy> policyList = convertHealthProposalToPolicy(healthProposalList);

      // create healthpolicy and return policynoList
      policyList = medicalPolicyRepo.saveAll(policyList);

      // create Workflow His
      List<String> workflowTaskList = Arrays.asList("SURVEY", "APPROVAL", "INFORM", "CONFIRMATION",
          "APPROVAL", "PAYMENT", "ISSUING");
      String referenceNo = policyList.get(0).getMedicalProposal().getId();
      String referenceType = "HEALTH_PROPOSAL";
      String createdDate = DateUtils.formattedSqlDate(new Date());
      String workflowDate = DateUtils.formattedSqlDate(new Date());
      int i = 0;
      for (String workflowTask : workflowTaskList) {
        String id = DateUtils.formattedSqlDate(new Date())
            .concat(healthProposalList.get(0).getProposalNo()).concat(String.valueOf(i));
        medicalProposalRepo.saveToWorkflowHistory(id, referenceNo, referenceType, workflowTask,
            createdDate, workflowDate);
        i++;
      }

      // create healthpolicy to payment
      List<Payment> paymentList = convertMedicalPolicyToPayment(policyList, paymentConfirmDate);
      paymentRepository.saveAll(paymentList);


      // create Agent Commission
      if (null != healthProposalDTO.getAgentID() && !healthProposalDTO.getAgentID().isEmpty()) {
        List<AgentCommission> agentcommissionList =
            convertMedicalPolicyToAgentCommission(policyList);
        CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
        recorder.setCreatedDate(new Date());
        agentcommissionList.forEach(agent -> {
          agent.setRecorder(recorder);
        });
        agentCommissionRepo.saveAll(agentcommissionList);

      }

      // create TLF
      List<TLF> TLFList = convertMedicalPolicyToTLF(policyList);
      tlfRepository.saveAll(TLFList);
      return policyList;
    } catch (Exception e) {
      logger.error("JOEERROR:" + e.getMessage(), e);
      throw e;
    }
  }

  // Forhealth healthDto to proposal
  public List<MedicalProposal> convertHealthProposalDTOToProposal(
      HealthProposalDTO healthProposalDTO) {
    List<MedicalProposal> medicalProposalList = new ArrayList<>();
    try {
      Optional<Branch> branchOptional = branchService.findById(healthProposalDTO.getBranchId());
      Optional<Customer> referralOptional =
          customerService.findById(healthProposalDTO.getReferralID());
      Optional<Customer> customerOptional =
          customerService.findById(healthProposalDTO.getCustomerID());
      Optional<PaymentType> paymentTypeOptional =
          paymentTypeService.findById(healthProposalDTO.getPaymentTypeId());
      Optional<Agent> agentOptional = agentService.findById(healthProposalDTO.getAgentID());
      Optional<SaleMan> saleManOptional = saleManService.findById(healthProposalDTO.getSaleManId());
      Optional<SalePoint> salePointOptional =
          salePointService.findById(healthProposalDTO.getSalePointId());

      healthProposalDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
        MedicalProposal medicalProposal = new MedicalProposal();

        if (healthProposalDTO.getPaymentChannel().equalsIgnoreCase("TRF")) {
          medicalProposal.setPaymentChannel(PaymentChannel.TRANSFER);
          medicalProposal.setToBank(healthProposalDTO.getToBank());
          medicalProposal.setFromBank(healthProposalDTO.getFromBank());
          medicalProposal.setChequeNo(healthProposalDTO.getChequeNo());
        } else if (healthProposalDTO.getPaymentChannel().equalsIgnoreCase("CSH")) {
          medicalProposal.setPaymentChannel(PaymentChannel.CASHED);
        } else if (healthProposalDTO.getPaymentChannel().equalsIgnoreCase("CHQ")) {
          medicalProposal.setPaymentChannel(PaymentChannel.CHEQUE);
          medicalProposal.setChequeNo(healthProposalDTO.getChequeNo());
          medicalProposal.setToBank(healthProposalDTO.getToBank());
          medicalProposal.setFromBank(healthProposalDTO.getFromBank());
        } else if (healthProposalDTO.getPaymentChannel().equalsIgnoreCase("RCV")) {
          medicalProposal.setPaymentChannel(PaymentChannel.SUNDRY);
          medicalProposal.setToBank(healthProposalDTO.getToBank());
          medicalProposal.setFromBank(healthProposalDTO.getFromBank());
        }
        if (paymentTypeOptional.isPresent()) {
          medicalProposal.setPaymentType(paymentTypeOptional.get());
        }

        medicalProposal.getMedicalProposalInsuredPersonList()
            .add(createInsuredPersonForHealth(insuredPerson, medicalProposal.getPaymentType()));


        if (customerOptional.isPresent()) {
          medicalProposal.setCustomer(customerOptional.get());
        }
        medicalProposal.setChannel(true);
        medicalProposal.setProposalType(ProposalType.UNDERWRITING);
        medicalProposal.setSubmittedDate(healthProposalDTO.getSubmittedDate());

        if (branchOptional.isPresent()) {
          medicalProposal.setBranch(branchOptional.get());
        }
        if (referralOptional.isPresent()) {
          medicalProposal.setReferral(referralOptional.get());
        }

        if (agentOptional.isPresent()) {
          medicalProposal.setAgent(agentOptional.get());
        }
        if (saleManOptional.isPresent()) {
          medicalProposal.setSaleMan(saleManOptional.get());
        }
        if (salePointOptional.isPresent()) {
          medicalProposal.setSalePoint(salePointOptional.get());
        }

        medicalProposal.setBpmsProposalNo(healthProposalDTO.getBpmsProposalNo());
        medicalProposal.setBpmsReceiptNo(healthProposalDTO.getBpmsReceiptNo());
        CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
        recorder.setCreatedDate(new Date());
        medicalProposal.setCommonCreateAndUpateMarks(recorder);
        String proposalNo = customIdRepo.getNextId("HEALTH_PROPOSAL_NO", null);
        medicalProposal.setProposalNo(proposalNo);
        medicalProposal.setChannel(true);
        medicalProposalList.add(medicalProposal);
      });
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
    return medicalProposalList;
  }

  private MedicalProposalInsuredPerson createInsuredPersonForHealth(
      HealthProposalInsuredPersonDTO dto, PaymentType paymentType) {
    try {
      Optional<Product> productOptional = productService.findById(healthProductId);
      Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
      Optional<Occupation> occupationOptional = occupationService.findById(dto.getOccupationID());
      Optional<Customer> customerOptional = customerService.findById(dto.getCustomerID());

      ResidentAddress residentAddress = new ResidentAddress();
      residentAddress.setResidentAddress(dto.getResidentAddress());
      residentAddress.setResidentTownship(townshipOptional.get());

      Name name = new Name();
      name.setFirstName(dto.getFirstName());
      name.setMiddleName(dto.getMiddleName());
      name.setLastName(dto.getLastName());

      MedicalProposalInsuredPerson insuredPerson = new MedicalProposalInsuredPerson();

      insuredPerson.setProduct(productOptional.get());
      insuredPerson.setBpmsInsuredPersonId(dto.getBpmsInsuredPersonId());
      insuredPerson.setProposedPremium(dto.getProposedPremium());

      insuredPerson.setApprovedPremium(dto.getApprovedPremium());
      insuredPerson.setBasicTermPremium(dto.getBasicTermPremium());

      insuredPerson.setEndDate(dto.getEndDate());
      insuredPerson.setStartDate(dto.getStartDate());
      insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
      insuredPerson.setPeriodMonth(12);
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      insuredPerson.setCommonCreateAndUpateMarks(recorder);

      insuredPerson.setCustomer(customerOptional.get());


      String insPersonCodeNo = customIdRepo.getNextId("MEDICAL_INSUREDPERSON_CODENO", null);
      insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
      dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
        insuredPerson.getInsuredPersonBeneficiariesList()
            .add(createInsuredPersonBeneficiareis(beneficiary));
      });
      return insuredPerson;
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
  }

  private MedicalProposalInsuredPersonBeneficiaries createInsuredPersonBeneficiareis(
      HealthInsuredPersonBeneficiaryDTO dto) {
    try {
      Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
      Optional<RelationShip> relationshipOptional =
          relationshipService.findById(dto.getRelationshipID());
      ResidentAddress residentAddress = new ResidentAddress();
      residentAddress.setResidentAddress(dto.getResidentAddress());

      residentAddress.setResidentTownship(townshipOptional.get());

      Name name = new Name();
      name.setFirstName(dto.getFirstName());
      name.setMiddleName(dto.getMiddleName());
      name.setLastName(dto.getLastName());

      MedicalProposalInsuredPersonBeneficiaries beneficiary =
          new MedicalProposalInsuredPersonBeneficiaries();
      beneficiary.setInitialId(dto.getInitialId());
      beneficiary.setPercentage(dto.getPercentage());
      beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
      beneficiary.setIdNo(dto.getIdNo());
      beneficiary.setGender(Gender.valueOf(dto.getGender()));
      beneficiary.setResidentAddress(residentAddress);
      beneficiary.setName(name);
      if (relationshipOptional.isPresent()) {
        beneficiary.setRelationship(relationshipOptional.get());
      }
      String beneficiaryNo = customIdRepo.getNextId("MEDI_BENEFICIARY_ID_GEN", null);
      beneficiary.setBeneficiaryNo(beneficiaryNo);
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());
      beneficiary.setCommonCreateAndUpateMarks(recorder);
      return beneficiary;
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    }
  }

  private List<MedicalPolicy> convertHealthProposalToPolicy(
      List<MedicalProposal> healthProposalList) {
    List<MedicalPolicy> policyList = new ArrayList<>();

    healthProposalList.forEach(proposal -> {
      MedicalPolicy policy = new MedicalPolicy(proposal);
      String policyNo = customIdRepo.getNextId("HEALTH_POLICY_NO", null);
      policy.setPolicyNo(policyNo);
      policy.setPaymentChannel(proposal.getPaymentChannel());
      policy.setFromBank(proposal.getFromBank());
      policy.setToBank(proposal.getToBank());
      policy.setChequeNo(proposal.getChequeNo());
      policy.setActivedPolicyStartDate(policy.getPolicyInsuredPersonList().get(0).getStartDate());

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(policy.getActivedPolicyStartDate());
      int month = proposal.getPaymentType() == null ? 0 : proposal.getPaymentType().getMonth();
      calendar.add(Calendar.MONTH, month);
      policy.setActivedPolicyEndDate(calendar.getTime());

      policy.setCommenmanceDate(proposal.getSubmittedDate());
      policy.setLastPaymentTerm(1);
      policy.setBpmsReceiptNo(proposal.getBpmsReceiptNo());
      // Payment EndDate
      Optional<Product> productOptional = productService.findById(healthProductId);
      int maxTerm = productOptional.get().getMaxTerm();

      Calendar cal = Calendar.getInstance();
      cal.setTime(policy.getActivedPolicyStartDate());
      policy.setPolicyStatus(PolicyStatus.INFORCE);
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(new Date());

      policy.setCommonCreateAndUpateMarks(recorder);
      policyList.add(policy);
    });
    return policyList;
  }

  private List<Payment> convertMedicalPolicyToPayment(List<MedicalPolicy> healthPolicyList,
      Date paymentConfirmDate) {
    List<Payment> paymentList = new ArrayList<Payment>();
    try {
      healthPolicyList.forEach(medicalPolicy -> {
        // String productId = medicalPolicy.getMedicalPolicyInsuredPerson(customer)
        Optional<Bank> fromBankOptional = Optional.empty();
        Optional<Bank> toBankOptional = Optional.empty();
        if (medicalPolicy.getFromBank() != null) {
          fromBankOptional = bankService.findById(medicalPolicy.getFromBank());
        }
        if (medicalPolicy.getToBank() != null) {
          toBankOptional = bankService.findById(medicalPolicy.getToBank());
        }
        Payment payment = new Payment();
        double rate = 1.0;
        String receiptNo = "";
        CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
        recorder.setCreatedDate(new Date());
        if (PaymentChannel.CASHED.equals(medicalPolicy.getPaymentChannel())) {
          receiptNo = customIdRepo.getNextId("CASH_RECEIPT_ID_GEN", null);
          payment.setPaymentChannel(PaymentChannel.CASHED);
        } else if (PaymentChannel.CHEQUE.equals(medicalPolicy.getPaymentChannel())) {
          payment.setPO(true);
          payment.setPaymentChannel(PaymentChannel.CHEQUE);
          receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
          payment.setChequeNo(medicalPolicy.getChequeNo());
        } else if (PaymentChannel.TRANSFER.equals(medicalPolicy.getPaymentChannel())) {
          payment.setPoNo(medicalPolicy.getChequeNo());
          payment.setPaymentChannel(PaymentChannel.TRANSFER);
          receiptNo = customIdRepo.getNextId("TRANSFER_RECEIPT_ID_GEN", null);
        } else if (PaymentChannel.SUNDRY.equals(medicalPolicy.getPaymentChannel())) {
          payment.setPaymentChannel(PaymentChannel.SUNDRY);
          receiptNo = customIdRepo.getNextId("CHEQUE_RECEIPT_ID_GEN", null);
          payment.setPO(true);
        }
        payment.setReceiptNo(receiptNo);
        payment.setPaymentType(medicalPolicy.getPaymentType());
        PolicyReferenceType policyReferenceType = null;

        policyReferenceType = PolicyReferenceType.HEALTH_POLICY;

        payment.setReferenceType(policyReferenceType);
        payment.setConfirmDate(paymentConfirmDate);
        payment.setPaymentDate(paymentConfirmDate);
        if (toBankOptional.isPresent()) {
          payment.setAccountBank(toBankOptional.get());
        }
        if (fromBankOptional.isPresent()) {
          payment.setBank(fromBankOptional.get());
        }
        payment.setBpmsReceiptNo(medicalPolicy.getBpmsReceiptNo());
        payment.setReferenceNo(medicalPolicy.getId());
        payment.setBasicPremium(medicalPolicy.getTotalBasicTermPremiumDouble());
        payment.setAddOnPremium(medicalPolicy.getTotalAddOnPremium());
        payment.setFromTerm(1);
        payment.setToTerm(1);
        payment.setCur("KYT");
        payment.setRate(rate);
        payment.setComplete(true);
        payment.setAmount(payment.getNetPremium());
        payment.setHomeAmount(payment.getNetPremium());
        payment.setHomePremium(payment.getBasicPremium());
        payment.setHomeAddOnPremium(payment.getAddOnPremium());
        payment.setCommonCreateAndUpateMarks(recorder);
        paymentList.add(payment);
      });
    } catch (DAOException e) {
      throw new SystemException(e.getErrorCode(), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return paymentList;
  }

  public List<AgentCommission> convertMedicalPolicyToAgentCommission(
      List<MedicalPolicy> medicalPolicyList) {
    List<AgentCommission> agentCommissionList = new ArrayList<AgentCommission>();
    try {
      /* get agent commission of each policy */
      medicalPolicyList.forEach(lifePolicy -> {
        Product product = lifePolicy.getPolicyInsuredPersonList().get(0).getProduct();
        PolicyReferenceType policyReferecncetype = null;

        policyReferecncetype = PolicyReferenceType.HEALTH_POLICY;

        double commissionPercent = product.getFirstCommission();
        Payment payment = paymentRepository.findByPaymentReferenceNo(lifePolicy.getId());
        double rate = payment.getRate();
        double firstAgentCommission = lifePolicy.getAgentCommission();
        Date startDate = payment.getConfirmDate();
        agentCommissionList.add(new AgentCommission(lifePolicy.getId(), policyReferecncetype,
            lifePolicy.getAgent(), firstAgentCommission, startDate, payment.getReceiptNo(),
            lifePolicy.getTotalTermPremium(), commissionPercent,
            AgentCommissionEntryType.UNDERWRITING, rate, (rate * firstAgentCommission), "KYT",
            (rate * lifePolicy.getTotalTermPremium()), payment.getBpmsReceiptNo()));
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return agentCommissionList;
  }

  public List<TLF> convertMedicalPolicyToTLF(List<MedicalPolicy> medicalPolicyList) {
    List<TLF> TLFList = new ArrayList<TLF>();
    try {
      String accountCode = "";
      for (MedicalPolicy medicalPolicy : medicalPolicyList) {
        Product product = medicalPolicy.getPolicyInsuredPersonList().get(0).getProduct();
        accountCode = product.getProductGroup().getAccountCode();
        Payment payment = paymentRepository.findByPaymentReferenceNo(medicalPolicy.getId());
        String customerId =
            medicalPolicy.getCustomer() == null ? medicalPolicy.getOrganization().getId()
                : medicalPolicy.getCustomer().getId();
        Branch policyBranch = medicalPolicy.getBranch();
        String currencyCode = "KYT";

        TLF premiumDebitTLF = addNewTLF_For_CashDebitForPremium(payment, customerId, policyBranch,
            payment.getReceiptNo(), false, currencyCode, medicalPolicy.getSalePoint(),
            medicalPolicy.getPolicyNo(), payment.getConfirmDate());
        TLFList.add(premiumDebitTLF);

        TLF premiumCreditTLF = addNewTLF_For_PremiumCredit(payment, customerId, policyBranch,
            accountCode, payment.getReceiptNo(), false, currencyCode, medicalPolicy.getSalePoint(),
            medicalPolicy.getPolicyNo(), payment.getConfirmDate());
        TLFList.add(premiumCreditTLF);

        if (medicalPolicy.getPaymentChannel().equals(PaymentChannel.CHEQUE)
            || medicalPolicy.getPaymentChannel().equals(PaymentChannel.SUNDRY)) {
          TLF tlf3 = addNewTLF_For_PremiumDebitForRCVAndCHQ(payment, customerId, policyBranch,
              payment.getAccountBank().getAcode(), false, payment.getReceiptNo(), true, false,
              currencyCode, medicalPolicy.getSalePoint(), medicalPolicy.getPolicyNo(),
              payment.getConfirmDate());
          TLFList.add(tlf3);

          TLF tlf4 = addNewTLF_For_CashCreditForPremiumForRCVAndCHQ(payment, customerId,
              policyBranch, false, payment.getReceiptNo(), true, false, "KYT",
              medicalPolicy.getSalePoint(), medicalPolicy.getPolicyNo(), payment.getConfirmDate());
          TLFList.add(tlf4);
        }

        if (medicalPolicy.getAgent() != null) {

          PolicyReferenceType policyReferenceType = null;
          double firstAgentCommission = medicalPolicy.getAgentCommission();


          policyReferenceType = PolicyReferenceType.HEALTH_POLICY;


          AgentCommission ac = new AgentCommission(medicalPolicy.getId(), policyReferenceType,
              medicalPolicy.getAgent(), firstAgentCommission, payment.getConfirmDate());

          TLF tlf5 = addNewTLF_For_AgentCommissionDr(ac, false, medicalPolicy.getBranch(), payment,
              payment.getId(), false, "KYT", medicalPolicy.getSalePoint(),
              medicalPolicy.getPolicyNo(), payment.getConfirmDate());
          TLFList.add(tlf5);

          TLF tlf6 = addNewTLF_For_AgentCommissionCredit(ac, false, medicalPolicy.getBranch(),
              payment, payment.getId(), false, "KYT", medicalPolicy.getSalePoint(),
              medicalPolicy.getPolicyNo(), payment.getConfirmDate());
          TLFList.add(tlf6);

        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return TLFList;
  }

  public TLF addNewTLF_For_CashDebitForPremium(Payment payment, String customerId, Branch branch,
      String tlfNo, boolean isRenewal, String currencyCode, SalePoint salePoint, String policyNo,
      Date startDate) {

    TLF tlf = null;

    try {
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(startDate);

      double totalNetPremium = 0;
      double homeAmount = 0;
      String coaCode = null;

      totalNetPremium = payment.getNetPremium();

      homeAmount = totalNetPremium;

      if (PaymentChannel.CASHED.equals(payment.getPaymentChannel())) {
        coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH,
            branch.getBranchCode(), currencyCode);
      } else if (PaymentChannel.TRANSFER.equals(payment.getPaymentChannel())) {
        coaCode = payment.getAccountBank() == null
            ? paymentRepository.findCheckOfAccountNameByCode(COACode.CHEQUE, branch.getBranchCode(),
                currencyCode)
            : payment.getAccountBank().getAcode();
      } else if (PaymentChannel.CHEQUE.equals(payment.getPaymentChannel())) {
        String coaCodeType = "";
        switch (payment.getReferenceType()) {

          case HEALTH_POLICY:
            coaCodeType = COACode.HEALTH_PAYMENT_ORDER;
            break;
          default:
            break;
        }
        coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
            branch.getBranchCode(), currencyCode);
      } else if (PaymentChannel.SUNDRY.equals(payment.getPaymentChannel())) {
        String coaCodeType = "";
        switch (payment.getReferenceType()) {

          case HEALTH_POLICY:
            coaCodeType = COACode.HEALTH_SUNDRY;
            break;
          default:
            break;
        }

        coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
            branch.getBranchCode(), currencyCode);
      }

      TLFBuilder tlfBuilder =
          new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId, branch.getBranchCode(), coaCode,
              tlfNo, getNarrationPremium(payment, isRenewal), payment, isRenewal);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setPolicyNo(policyNo);
      tlf.setSalePoint(salePoint);
      tlf.setCommonCreateAndUpateMarks(recorder);
      tlf.setPaid(true);
      // setIDPrefixForInsert(tlf);
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
      tlf.setSettlementDate(startDate);
      // paymentDAO.insertTLF(tlf);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }

  private String getNarrationPremium(Payment payment, boolean isRenewal) {
    StringBuffer nrBf = new StringBuffer();
    String customerName = "";
    String premiumString = "";
    int totalInsuredPerson = 0;
    double si = 0.0;
    double premium = 0.0;

    nrBf.append("Being amount of ");
    switch (payment.getReferenceType()) {


      case HEALTH_POLICY:
        nrBf.append("Health Premium ");
        MedicalPolicy spclifePolicy = medicalPolicyRepo.getOne(payment.getReferenceNo());
        si = spclifePolicy.getTotalSumInsured();
        premium = spclifePolicy.getTotalPremium();
        customerName = spclifePolicy.getCustomerName();
      default:
        break;
    }
    nrBf.append(premiumString);
    nrBf.append(" received by ");
    nrBf.append(payment.getReceiptNo());
    nrBf.append(" from ");
    nrBf.append(customerName);
    nrBf.append(" for Sum Insured ");
    nrBf.append(Utils.getCurrencyFormatString(si));
    if (PolicyReferenceType.HEALTH_POLICY.equals(payment.getReferenceType())) {
      nrBf.append(" and for total number of insured person ");
      nrBf.append(Integer.toString(totalInsuredPerson));
    }

    nrBf.append(" and the premium amount of ");
    nrBf.append(Utils.getCurrencyFormatString(premium));

    nrBf.append(". ");

    return nrBf.toString();
  }

  public TLF addNewTLF_For_PremiumCredit(Payment payment, String customerId, Branch branch,
      String accountName, String tlfNo, boolean isRenewal, String currenyCode, SalePoint salePoint,
      String policyNo, Date startDate) {
    TLF tlf = null;

    try {
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(startDate);
      double homeAmount = payment.getNetPremium();
      if (isRenewal) {
        homeAmount = payment.getRenewalNetPremium();
      }

      String coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName,
          branch.getBranchCode(), currenyCode);
      TLFBuilder tlfBuilder =
          new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId, branch.getBranchCode(),
              coaCode, tlfNo, getNarrationPremium(payment, isRenewal), payment, isRenewal);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setPolicyNo(policyNo);
      tlf.setCommonCreateAndUpateMarks(recorder);
      tlf.setPaid(true);
      tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
      tlf.setSettlementDate(startDate);
      // setIDPrefixForInsert(tlf);
      /// paymentDAO.insertTLF(tlf);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }

  public TLF addNewTLF_For_PremiumDebitForRCVAndCHQ(Payment payment, String customerId,
      Branch branch, String accountName, boolean isEndorse, String tlfNo, boolean isClearing,
      boolean isRenewal, String currencyCode, SalePoint salePoint, String policyNo,
      Date startDate) {
    TLF tlf = new TLF();
    try {
      double netPremium = 0.0;
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(startDate);
      if (isRenewal) {
        netPremium = payment.getRenewalNetPremium();
      } else {
        netPremium = payment.getNetPremium();
      }

      netPremium = netPremium * (isEndorse ? -1 : 1);
      double homeAmount = 0;
      // TLF Home Amount
      homeAmount = netPremium;

      // TLF COAID
      String coaCode = "";
      if (payment.getAccountBank() == null) {
        coaCode = paymentRepository.findCheckOfAccountNameByCode(accountName,
            branch.getBranchCode(), currencyCode);
      } else {
        coaCode = payment.getAccountBank().getAcode();
      }

      TLFBuilder tlfBuilder =
          new TLFBuilder(DoubleEntry.DEBIT, homeAmount, customerId, branch.getBranchCode(), coaCode,
              tlfNo, getNarrationPremium(payment, isRenewal), payment, isRenewal);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setClearing(isClearing);
      tlf.setCommonCreateAndUpateMarks(recorder);
      tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
      tlf.setSettlementDate(startDate);
      // tlf.setPolicyNo(policyNo);
      // setIDPrefixForInsert(tlf);
      // paymentDAO.insertTLF(tlf);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }

  public TLF addNewTLF_For_CashCreditForPremiumForRCVAndCHQ(Payment payment, String customerId,
      Branch branch, boolean isEndorse, String tlfNo, boolean isClearing, boolean isRenewal,
      String currencyCode, SalePoint salePoint, String policyNo, Date startDate) {
    TLF tlf = new TLF();
    try {

      double totalNetPremium = 0;
      double homeAmount = 0;
      String narration = null;
      String coaCode = null;
      String enoNo = payment.getReceiptNo();
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(startDate);
      if (isRenewal) {
        totalNetPremium = totalNetPremium + payment.getRenewalNetPremium();
      } else {
        totalNetPremium = totalNetPremium + payment.getNetPremium();
      }

      totalNetPremium = totalNetPremium * (isEndorse ? -1 : 1);

      // TLF Home Amount
      homeAmount = totalNetPremium;

      // TLF COAID
      switch (payment.getPaymentChannel()) {
        case TRANSFER: {
          if (payment.getAccountBank() == null) {
            coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CHEQUE,
                branch.getBranchCode(), currencyCode);
          } else {
            coaCode = payment.getAccountBank().getAcode();
          }
        }
          break;
        case CASHED:
          coaCode = paymentRepository.findCheckOfAccountNameByCode(COACode.CASH,
              branch.getBranchCode(), currencyCode);
          break;
        case CHEQUE: {
          String coaCodeType = "";
          switch (payment.getReferenceType()) {

            case HEALTH_POLICY:
              coaCodeType = COACode.HEALTH_PAYMENT_ORDER;
              break;
            default:
              break;
          }
          coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
              branch.getBranchCode(), currencyCode);
        }
          break;
        case SUNDRY: {
          String coaCodeType = "";
          switch (payment.getReferenceType()) {

            case HEALTH_POLICY:
              coaCodeType = COACode.HEALTH_SUNDRY;
              break;
            default:
              break;
          }
          coaCode = paymentRepository.findCheckOfAccountNameByCode(coaCodeType,
              branch.getBranchCode(), currencyCode);
        }
          break;
      }
      // TLF Narration
      narration = "Cash refund for " + enoNo;
      TLFBuilder tlfBuilder = new TLFBuilder(DoubleEntry.CREDIT, homeAmount, customerId,
          branch.getBranchCode(), coaCode, tlfNo, narration, payment, isRenewal);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setClearing(isClearing);
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setCommonCreateAndUpateMarks(recorder);
      tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
      tlf.setSettlementDate(startDate);
      // tlf.setPolicyNo(policyNo);
      // setIDPrefixForInsert(tlf);
      /// paymentDAO.insertTLF(tlf);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }

  public TLF addNewTLF_For_AgentCommissionDr(AgentCommission ac, boolean coInsureance,
      Branch branch, Payment payment, String eno, boolean isRenewal, String currencyCode,
      SalePoint salePoint, String policyNo, Date startDate) {
    TLF tlf = new TLF();
    try {
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(startDate);
      String receiptNo = payment.getReceiptNo();
      String coaCode = null;
      String coaCodeMI = null;
      String accountName = null;
      double ownCommission = 0.0;

      switch (ac.getReferenceType()) {

        case HEALTH_POLICY:
          coaCode = COACode.HEALTH_AGENT_COMMISSION;
          break;
        default:
          break;
      }
      String cur = payment.getCur();
      double rate = payment.getRate();
      String narration = getNarrationAgent(payment, ac, isRenewal);

      accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(),
          currencyCode);
      ownCommission = Utils.getTwoDecimalPoint(ac.getCommission());

      TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRDEBIT, Status.TDV, ownCommission,
          ac.getAgent().getId(), branch.getBranchCode(), accountName, receiptNo, narration, eno,
          ac.getReferenceNo(), ac.getReferenceType(), isRenewal, cur, rate);
      tlf = tlfBuilder.getTLFInstance();
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setPolicyNo(policyNo);
      tlf.setAgentTransaction(true);
      tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
      tlf.setPaid(true);
      tlf.setCommonCreateAndUpateMarks(recorder);
      tlf.setSettlementDate(startDate);
      // setIDPrefixForInsert(tlf);
      // paymentDAO.insertTLF(tlf);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }

  public TLF addNewTLF_For_AgentCommissionCredit(AgentCommission ac, boolean coInsureance,
      Branch branch, Payment payment, String eno, boolean isRenewal, String currencyCode,
      SalePoint salePoint, String policyNo, Date startDate) {
    TLF tlf = new TLF();
    try {
      CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
      recorder.setCreatedDate(startDate);
      String receiptNo = payment.getReceiptNo();
      String coaCode = null;
      String accountName = null;
      double commission = 0.0;

      switch (ac.getReferenceType()) {

        case HEALTH_POLICY:
          coaCode = COACode.HEALTH_AGENT_PAYABLE;
          break;
        default:
          break;
      }

      accountName = paymentRepository.findCheckOfAccountNameByCode(coaCode, branch.getBranchCode(),
          currencyCode);
      commission = Utils.getTwoDecimalPoint(ac.getCommission());
      String narration = getNarrationAgent(payment, ac, isRenewal);
      String cur = payment.getCur();
      double rate = payment.getRate();
      TLFBuilder tlfBuilder = new TLFBuilder(TranCode.TRCREDIT, Status.TCV, commission,
          ac.getAgent().getId(), branch.getBranchCode(), accountName, receiptNo, narration, eno,
          ac.getReferenceNo(), ac.getReferenceType(), isRenewal, cur, rate);
      tlf = tlfBuilder.getTLFInstance();
      // setIDPrefixForInsert(tlf);
      tlf.setPaymentChannel(payment.getPaymentChannel());
      tlf.setSalePoint(salePoint);
      tlf.setPolicyNo(policyNo);
      tlf.setBpmsReceiptNo(payment.getBpmsReceiptNo());
      tlf.setPaid(true);
      tlf.setAgentTransaction(true);
      tlf.setSettlementDate(startDate);
      tlf.setCommonCreateAndUpateMarks(recorder);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return tlf;
  }


  private String getNarrationAgent(Payment payment, AgentCommission agentCommission,
      boolean isRenewal) {
    StringBuffer nrBf = new StringBuffer();
    double commission = 0.0;
    String agentName = "";
    String insuranceName = "";
    // Agent Commission payable for Fire Insurance(Product Name), Received
    // No to Agent Name for commission amount of Amount
    nrBf.append("Agent Commission payable for ");
    switch (payment.getReferenceType()) {

      case HEALTH_POLICY:
        insuranceName = "Health Insurance,";
        break;
      default:
        break;
    }
    agentName = agentCommission.getAgent() == null ? "" : agentCommission.getAgent().getFullName();
    commission = agentCommission.getCommission();
    nrBf.append(insuranceName);
    nrBf.append(payment.getReceiptNo());
    nrBf.append(" to ");
    nrBf.append(agentName);
    nrBf.append(" for commission amount of ");
    nrBf.append(Utils.getCurrencyFormatString(commission));
    nrBf.append(".");

    return nrBf.toString();
  }



}
