package org.tat.gginl.api.domains.services.productServices;

import java.util.ArrayList;
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
import org.tat.gginl.api.common.CommonCreateAndUpateMarks;
import org.tat.gginl.api.common.DateUtils;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.emumdata.ClassificationOfHealth;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyStatus;
import org.tat.gginl.api.common.emumdata.ProposalType;
import org.tat.gginl.api.common.emumdata.SumInsuredType;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.AgentCommission;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.LifeProposal;
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.Organization;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.PaymentType;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.domains.ProposalInsuredPerson;
import org.tat.gginl.api.domains.SaleMan;
import org.tat.gginl.api.domains.SalePoint;
import org.tat.gginl.api.domains.TLF;
import org.tat.gginl.api.domains.Township;
import org.tat.gginl.api.domains.repository.AgentCommissionRepository;
import org.tat.gginl.api.domains.repository.LifePolicyRepository;
import org.tat.gginl.api.domains.repository.PaymentRepository;
import org.tat.gginl.api.domains.repository.TLFRepository;
import org.tat.gginl.api.domains.services.AgentService;
import org.tat.gginl.api.domains.services.BranchService;
import org.tat.gginl.api.domains.services.CustomerService;
import org.tat.gginl.api.domains.services.ICustomIdGenerator;
import org.tat.gginl.api.domains.services.LifeProposalService;
import org.tat.gginl.api.domains.services.OccupationService;
import org.tat.gginl.api.domains.services.OrganizationService;
import org.tat.gginl.api.domains.services.PaymentTypeService;
import org.tat.gginl.api.domains.services.ProductService;
import org.tat.gginl.api.domains.services.SaleManService;
import org.tat.gginl.api.domains.services.SalePointService;
import org.tat.gginl.api.domains.services.TownShipService;
import org.tat.gginl.api.dto.shortTermSinglePremiumCreditLifeDTO.ShortTermSinglePremiumCreditLifeDTO;
import org.tat.gginl.api.dto.shortTermSinglePremiumCreditLifeDTO.ShortTermSinglePremiumCreditLifeProposalInsuredPersonDTO;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.SystemException;

/**
 * Created by Sai Nyan Htay
 */

@Service
@PropertySource("classpath:keyfactor-id-config.properties")
public class ShortTermSinglePremiumCreditLifeService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LifePolicyRepository lifePolicyRepo;

	@Autowired
	private BranchService branchService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private LifeProposalService lifeProposalService;

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
	private ICustomIdGenerator customIdRepo;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private TLFRepository tlfRepository;

	@Autowired
	private AgentCommissionRepository agentCommissionRepo;

	@Value("${LUMPSUM}")
	private String LUMPSUM;
	
	@Value("${INSUPERSON_PREFIX}")
	private String insuredPersonPrefix;
	
	@Value("${LIFEPROPOSAL_PREFIX}")
	private String lifeProposalPrefix;

	@Value("${shortTermSinglePremiumCreditLifeProductId}")
	private String shortTermSinglePremiumCreditLifeProductId;
	
	
	public static final String LIFE_INSUREDPERSON_CODENO_ID_GEN = "LIFE_INSUREDPERSON_CODENO_ID_GEN";
	public static final String SHORT_TERM_SINGLE_PREMIUM_CREDIT_LIFE_PROPOSAL_NO = "SHORT_TERM_SINGLE_PREMIUM_CREDIT_LIFE_PROPOSAL_NO";
	public static final String SHORT_TERM_SINGLE_PREMIUM_CREDIT_LIFE_POLICY_NO = "SHORT_TERM_SINGLE_PREMIUM_CREDIT_LIFE_POLICY_NO"; 

	/* Short Term Single Premium Credit Life Service */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> createShortTermSinglePremiumCreditLifePolicy(ShortTermSinglePremiumCreditLifeDTO shortTermSinglePremiumCreditLifeDTO) {
		try {
			List<LifeProposal> shortTermSinglePremiumCreditLifeProposalList = convertShortTermSinglePremiumCreditLifeProposalDTOToProposal(shortTermSinglePremiumCreditLifeDTO);
			Date paymentConfirmDate = shortTermSinglePremiumCreditLifeDTO.getPaymentConfirmDate();
			// convert lifeproposal to lifepolicy
			List<LifePolicy> policyList = convertShortTermSinglePremiumCreditLifeProposalToPolicy(shortTermSinglePremiumCreditLifeProposalList);

			// create lifepolicy and return policynoList
			policyList = lifePolicyRepo.saveAll(policyList);

			// create lifepolicy to payment
			List<Payment> paymentList = lifeProposalService.convertLifePolicyToPayment(policyList, paymentConfirmDate);
			paymentRepository.saveAll(paymentList);

			// create Agent Commission
			if (null != shortTermSinglePremiumCreditLifeDTO.getAgentID()) {
				List<AgentCommission> agentcommissionList = lifeProposalService.convertLifePolicyToAgentCommission(policyList);
				CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
				recorder.setCreatedDate(new Date());
				agentcommissionList.forEach(agent -> {
					agent.setRecorder(recorder);
				});
				agentCommissionRepo.saveAll(agentcommissionList);

			}

			// create TLF
			List<TLF> TLFList = lifeProposalService.convertLifePolicyToTLF(policyList);
			tlfRepository.saveAll(TLFList);
			return policyList;
		} catch (Exception e) {
			logger.error("JOEERROR:" + e.getMessage(), e);
			throw e;
		}
	}

	public List<LifeProposal> convertShortTermSinglePremiumCreditLifeProposalDTOToProposal(ShortTermSinglePremiumCreditLifeDTO shortTermSinglePremiumCreditLifeDTO) {
		List<LifeProposal> lifeProposalList = new ArrayList<>();
		try {
			Optional<Branch> branchOptinal = branchService.findById(shortTermSinglePremiumCreditLifeDTO.getBranchId());
			Optional<Customer> referralOptional = customerService
					.findById(shortTermSinglePremiumCreditLifeDTO.getReferralID());
			Optional<Customer> customerOptional = customerService
					.findById(shortTermSinglePremiumCreditLifeDTO.getCustomerID());
			Optional<Organization> organizationOptinal = organizationService
					.findById(shortTermSinglePremiumCreditLifeDTO.getOrganizationID());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService
					.findById(shortTermSinglePremiumCreditLifeDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(shortTermSinglePremiumCreditLifeDTO.getAgentID());
			Optional<SaleMan> saleManOptional = saleManService
					.findById(shortTermSinglePremiumCreditLifeDTO.getSaleManId());
			Optional<SalePoint> salePointOptional = salePointService
					.findById(shortTermSinglePremiumCreditLifeDTO.getSalePointId());

			// check validation
//			checkValidationProposal(shortTermSinglePremiumCreditLifeDTO);

			shortTermSinglePremiumCreditLifeDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
				LifeProposal lifeProposal = new LifeProposal();
				if (shortTermSinglePremiumCreditLifeDTO.getPaymentChannel().equalsIgnoreCase("TRF")) {
					lifeProposal.setPaymentChannel(PaymentChannel.TRANSFER);
					lifeProposal.setToBank(shortTermSinglePremiumCreditLifeDTO.getToBank());
					lifeProposal.setFromBank(shortTermSinglePremiumCreditLifeDTO.getFromBank());
					lifeProposal.setChequeNo(shortTermSinglePremiumCreditLifeDTO.getChequeNo());
				} else if (shortTermSinglePremiumCreditLifeDTO.getPaymentChannel().equalsIgnoreCase("CSH")) {
					lifeProposal.setPaymentChannel(PaymentChannel.CASHED);
				} else if (shortTermSinglePremiumCreditLifeDTO.getPaymentChannel().equalsIgnoreCase("CHQ")) {
					lifeProposal.setPaymentChannel(PaymentChannel.CHEQUE);
					lifeProposal.setChequeNo(shortTermSinglePremiumCreditLifeDTO.getChequeNo());
					lifeProposal.setToBank(shortTermSinglePremiumCreditLifeDTO.getToBank());
					lifeProposal.setFromBank(shortTermSinglePremiumCreditLifeDTO.getFromBank());
				} else if (shortTermSinglePremiumCreditLifeDTO.getPaymentChannel().equalsIgnoreCase("RCV")) {
					lifeProposal.setPaymentChannel(PaymentChannel.SUNDRY);
					lifeProposal.setToBank(shortTermSinglePremiumCreditLifeDTO.getToBank());
					lifeProposal.setFromBank(shortTermSinglePremiumCreditLifeDTO.getFromBank());
				}

				lifeProposal.getProposalInsuredPersonList().add(createShortTermSinglePremiumCreditLifeInsuredPerson(insuredPerson));

				lifeProposal.setComplete(true);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal.setSubmittedDate(shortTermSinglePremiumCreditLifeDTO.getSubmittedDate());

				if (customerOptional.isPresent()) {
					lifeProposal.setCustomer(customerOptional.get());
				}

				if (organizationOptinal.isPresent()) {
					lifeProposal.setOrganization(organizationOptinal.get());
				}

				if (branchOptinal.isPresent()) {
					lifeProposal.setBranch(branchOptinal.get());
				}

				if (referralOptional.isPresent()) {
					lifeProposal.setReferral(referralOptional.get());
				}

				if (agentOptional.isPresent()) {
					lifeProposal.setAgent(agentOptional.get());
				}
				if (saleManOptional.isPresent()) {
					lifeProposal.setSaleMan(saleManOptional.get());
				}
				if (salePointOptional.isPresent()) {
					lifeProposal.setSalePoint(salePointOptional.get());
				}
				if (paymentTypeOptional.isPresent()) {
					lifeProposal.setPaymentType(paymentTypeOptional.get());
				}
				CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
				recorder.setCreatedDate(new Date());
				lifeProposal.setRecorder(recorder);
				lifeProposal.setBpmsProposalNo(shortTermSinglePremiumCreditLifeDTO.getBpmsProposalNo());
				lifeProposal.setBpmsReceiptNo(shortTermSinglePremiumCreditLifeDTO.getBpmsReceiptNo());
				String proposalNo = customIdRepo.getNextId(SHORT_TERM_SINGLE_PREMIUM_CREDIT_LIFE_PROPOSAL_NO, null);
				lifeProposal.setProposalNo(proposalNo);
				lifeProposal.setPrefix(lifeProposalPrefix);
				lifeProposalList.add(lifeProposal);
			});
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
		return lifeProposalList;
	}
	

	private ProposalInsuredPerson createShortTermSinglePremiumCreditLifeInsuredPerson(ShortTermSinglePremiumCreditLifeProposalInsuredPersonDTO dto) {
		try {
			Optional<Product> productOptional = productService.findById(shortTermSinglePremiumCreditLifeProductId);
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

			ProposalInsuredPerson insuredPerson = new ProposalInsuredPerson();

			insuredPerson.setProduct(productOptional.get());
			insuredPerson.setInitialId(dto.getInitialId());
			insuredPerson.setBpmsInsuredPersonId(dto.getBpmsInsuredPersonId());
			insuredPerson.setProposedSumInsured(dto.getProposedSumInsured());
			insuredPerson.setProposedPremium(dto.getProposedPremium());
			insuredPerson.setApprovedSumInsured(dto.getApprovedSumInsured());
			insuredPerson.setApprovedPremium(dto.getApprovedPremium());
			insuredPerson.setBasicTermPremium(dto.getBasicTermPremium());
			insuredPerson.setIdType(IdType.valueOf(dto.getIdType()));
			insuredPerson.setIdNo(dto.getIdNo());
			insuredPerson.setFatherName(dto.getFatherName());
			insuredPerson.setStartDate(dto.getStartDate());
			insuredPerson.setEndDate(dto.getEndDate());
			insuredPerson.setDateOfBirth(dto.getDateOfBirth());
			insuredPerson.setAge(DateUtils.getAgeForNextYear(dto.getDateOfBirth()));
			insuredPerson.setPeriodMonth(12);
			insuredPerson.setGender(Gender.valueOf(dto.getGender()));
			insuredPerson.setResidentAddress(residentAddress);
			insuredPerson.setClsOfHealth(ClassificationOfHealth.FIRSTCLASS);
			insuredPerson.setName(name);
			insuredPerson.setSumInsuredType(SumInsuredType.valueOf(dto.getSumInsuredType()));
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			insuredPerson.setRecorder(recorder);
			if (occupationOptional.isPresent()) {
				insuredPerson.setOccupation(occupationOptional.get());
			}
			if (customerOptional.isPresent()) {
				insuredPerson.setCustomer(customerOptional.get());
			} else {
				insuredPerson.setCustomer(lifeProposalService.createNewCustomer(insuredPerson));
				insuredPerson.setNewCustomer(true);
			}

			String insPersonCodeNo = customIdRepo.getNextId(LIFE_INSUREDPERSON_CODENO_ID_GEN, null);
			insuredPerson.setInsPersonCodeNo(insPersonCodeNo);
			insuredPerson.setPrefix(insuredPersonPrefix);
			dto.getInsuredPersonBeneficiariesList().forEach(beneficiary -> {
				insuredPerson.getInsuredPersonBeneficiariesList().add(lifeProposalService.createSinglePremiumCreditLifeInsuredPersonBeneficiareis(beneficiary));
			});
			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	private List<LifePolicy> convertShortTermSinglePremiumCreditLifeProposalToPolicy(List<LifeProposal> shortTermSinglePremiumCreditLifeProposalList) {
		List<LifePolicy> policyList = new ArrayList<>();
		shortTermSinglePremiumCreditLifeProposalList.forEach(proposal -> {
			LifePolicy policy = new LifePolicy(proposal);
			String policyNo = customIdRepo.getNextId(SHORT_TERM_SINGLE_PREMIUM_CREDIT_LIFE_POLICY_NO, null);
			policy.setPolicyNo(policyNo);
			policy.setPaymentChannel(proposal.getPaymentChannel());
			policy.setFromBank(proposal.getFromBank());
			policy.setBpmsReceiptNo(proposal.getBpmsReceiptNo());
			policy.setToBank(proposal.getToBank());
			policy.setChequeNo(proposal.getChequeNo());
			policy.setActivedPolicyStartDate(policy.getPolicyInsuredPersonList().get(0).getStartDate());
			policy.setActivedPolicyEndDate(policy.getPolicyInsuredPersonList().get(0).getEndDate());
			policy.setCommenmanceDate(proposal.getSubmittedDate());
			policy.setLastPaymentTerm(1);

			policy.setPolicyStatus(PolicyStatus.INFORCE);
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			policy.setRecorder(recorder);
			policyList.add(policy);
		});
		return policyList;
	}
}
