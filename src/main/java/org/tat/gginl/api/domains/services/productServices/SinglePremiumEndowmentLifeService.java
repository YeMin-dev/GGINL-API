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
import org.tat.gginl.api.common.ContentInfo;
import org.tat.gginl.api.common.DateUtils;
import org.tat.gginl.api.common.Name;
import org.tat.gginl.api.common.ResidentAddress;
import org.tat.gginl.api.common.emumdata.ClassificationOfHealth;
import org.tat.gginl.api.common.emumdata.Gender;
import org.tat.gginl.api.common.emumdata.IdType;
import org.tat.gginl.api.common.emumdata.PaymentChannel;
import org.tat.gginl.api.common.emumdata.PolicyStatus;
import org.tat.gginl.api.common.emumdata.ProposalType;
import org.tat.gginl.api.domains.Agent;
import org.tat.gginl.api.domains.AgentCommission;
import org.tat.gginl.api.domains.Branch;
import org.tat.gginl.api.domains.Customer;
import org.tat.gginl.api.domains.InsuredPersonBeneficiaries;
import org.tat.gginl.api.domains.LifePolicy;
import org.tat.gginl.api.domains.LifeProposal;
import org.tat.gginl.api.domains.Occupation;
import org.tat.gginl.api.domains.Organization;
import org.tat.gginl.api.domains.Payment;
import org.tat.gginl.api.domains.PaymentType;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.domains.ProposalInsuredPerson;
import org.tat.gginl.api.domains.RelationShip;
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
import org.tat.gginl.api.domains.services.RelationshipService;
import org.tat.gginl.api.domains.services.SaleManService;
import org.tat.gginl.api.domains.services.SalePointService;
import org.tat.gginl.api.domains.services.TownShipService;
import org.tat.gginl.api.dto.singlePremiumEndowmentLifeDTO.SinglePremiumEndowmentLifeDTO;
import org.tat.gginl.api.dto.singlePremiumEndowmentLifeDTO.SinglePremiumEndowmentLifeInsuredPersonBeneficiaryDTO;
import org.tat.gginl.api.dto.singlePremiumEndowmentLifeDTO.SinglePremiumEndowmentLifeProposalInsuredPersonDTO;
import org.tat.gginl.api.exception.DAOException;
import org.tat.gginl.api.exception.SystemException;

/**
 * Created by Sai Nyan Htay
 */

@Service
@PropertySource("classpath:keyfactor-id-config.properties")
public class SinglePremiumEndowmentLifeService {
	
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
	private RelationshipService relationshipService;

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
	
	@Value("${INSUPERSON_BENE_PREFIX}")
	private String insuPersonBenePrefix;

	@Value("${singlePremiumEndowmentLifeProductId}")
	private String singlePremiumEndowmentLifeProductId;
	
	public static final String LIFE_INSUREDPERSON_CODENO_ID_GEN = "LIFE_INSUREDPERSON_CODENO_ID_GEN";
	public static final String SINGLE_PREMIUM_ENDOWMENT_LIFE_PROPOSAL_NO = "SINGLE_PREMIUM_ENDOWMENT_LIFE_PROPOSAL_NO";
	public static final String SINGLE_PREMIUM_ENDOWMENT_LIFE_POLICY_NO = "SINGLE_PREMIUM_ENDOWMENT_LIFE_POLICY_NO"; 

	/* Single Premium Endowment Life Service */
	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePolicy> createSinglePremiumEndowmentLifePolicy(SinglePremiumEndowmentLifeDTO singlePremiumEndowmentLifeDTO) {
		try {
			List<LifeProposal> singlePremiumEndowmentLifeProposalList = convertSinglePremiumEndowmentLifeProposalDTOToProposal(singlePremiumEndowmentLifeDTO);
			Date paymentConfirmDate = singlePremiumEndowmentLifeDTO.getPaymentConfirmDate();
			// convert lifeproposal to lifepolicy
			List<LifePolicy> policyList = convertSinglePremiumEndowmentLifeProposalToPolicy(singlePremiumEndowmentLifeProposalList);

			// create lifepolicy and return policynoList
			policyList = lifePolicyRepo.saveAll(policyList);

			// create lifepolicy to payment
			List<Payment> paymentList = lifeProposalService.convertLifePolicyToPayment(policyList, paymentConfirmDate);
			paymentRepository.saveAll(paymentList);

			// create Agent Commission
			if (null != singlePremiumEndowmentLifeDTO.getAgentID()) {
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

	public List<LifeProposal> convertSinglePremiumEndowmentLifeProposalDTOToProposal(SinglePremiumEndowmentLifeDTO singlePremiumEndowmentLifeDTO) {
		List<LifeProposal> lifeProposalList = new ArrayList<>();
		try {
			Optional<Branch> branchOptinal = branchService.findById(singlePremiumEndowmentLifeDTO.getBranchId());
			Optional<Customer> referralOptional = customerService
					.findById(singlePremiumEndowmentLifeDTO.getReferralID());
			Optional<Customer> customerOptional = customerService
					.findById(singlePremiumEndowmentLifeDTO.getCustomerID());
			Optional<PaymentType> paymentTypeOptional = paymentTypeService
					.findById(singlePremiumEndowmentLifeDTO.getPaymentTypeId());
			Optional<Agent> agentOptional = agentService.findById(singlePremiumEndowmentLifeDTO.getAgentID());
			Optional<SaleMan> saleManOptional = saleManService
					.findById(singlePremiumEndowmentLifeDTO.getSaleManId());
			Optional<SalePoint> salePointOptional = salePointService
					.findById(singlePremiumEndowmentLifeDTO.getSalePointId());

			// check validation
//			checkValidationProposal(shortTermSinglePremiumCreditLifeDTO);

			singlePremiumEndowmentLifeDTO.getProposalInsuredPersonList().forEach(insuredPerson -> {
				LifeProposal lifeProposal = new LifeProposal();
				if (singlePremiumEndowmentLifeDTO.getPaymentChannel().equalsIgnoreCase("TRF")) {
					lifeProposal.setPaymentChannel(PaymentChannel.TRANSFER);
					lifeProposal.setToBank(singlePremiumEndowmentLifeDTO.getToBank());
					lifeProposal.setFromBank(singlePremiumEndowmentLifeDTO.getFromBank());
					lifeProposal.setChequeNo(singlePremiumEndowmentLifeDTO.getChequeNo());
				} else if (singlePremiumEndowmentLifeDTO.getPaymentChannel().equalsIgnoreCase("CSH")) {
					lifeProposal.setPaymentChannel(PaymentChannel.CASHED);
				} else if (singlePremiumEndowmentLifeDTO.getPaymentChannel().equalsIgnoreCase("CHQ")) {
					lifeProposal.setPaymentChannel(PaymentChannel.CHEQUE);
					lifeProposal.setChequeNo(singlePremiumEndowmentLifeDTO.getChequeNo());
					lifeProposal.setToBank(singlePremiumEndowmentLifeDTO.getToBank());
					lifeProposal.setFromBank(singlePremiumEndowmentLifeDTO.getFromBank());
				} else if (singlePremiumEndowmentLifeDTO.getPaymentChannel().equalsIgnoreCase("RCV")) {
					lifeProposal.setPaymentChannel(PaymentChannel.SUNDRY);
					lifeProposal.setToBank(singlePremiumEndowmentLifeDTO.getToBank());
					lifeProposal.setFromBank(singlePremiumEndowmentLifeDTO.getFromBank());
				}

				lifeProposal.getProposalInsuredPersonList().add(createSinglePremiumEndowmentLifeInsuredPerson(insuredPerson));

				lifeProposal.setComplete(true);
				lifeProposal.setProposalType(ProposalType.UNDERWRITING);
				lifeProposal.setSubmittedDate(singlePremiumEndowmentLifeDTO.getSubmittedDate());

				if (customerOptional.isPresent()) {
					lifeProposal.setCustomer(customerOptional.get());
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
				lifeProposal.setBpmsProposalNo(singlePremiumEndowmentLifeDTO.getBpmsProposalNo());
				lifeProposal.setBpmsReceiptNo(singlePremiumEndowmentLifeDTO.getBpmsReceiptNo());
				String proposalNo = customIdRepo.getNextId(SINGLE_PREMIUM_ENDOWMENT_LIFE_PROPOSAL_NO, null);
				lifeProposal.setProposalNo(proposalNo);
				lifeProposal.setPrefix(lifeProposalPrefix);
				lifeProposalList.add(lifeProposal);
			});
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
		return lifeProposalList;
	}
	

	private ProposalInsuredPerson createSinglePremiumEndowmentLifeInsuredPerson(SinglePremiumEndowmentLifeProposalInsuredPersonDTO dto) {
		try {
			Optional<Product> productOptional = productService.findById(singlePremiumEndowmentLifeProductId);
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
				insuredPerson.getInsuredPersonBeneficiariesList().add(createSinglePremiumEndowmentLifeInsuredPersonBeneficiareis(beneficiary));
			});
			return insuredPerson;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}
	
	private InsuredPersonBeneficiaries createSinglePremiumEndowmentLifeInsuredPersonBeneficiareis(SinglePremiumEndowmentLifeInsuredPersonBeneficiaryDTO dto) {
		try {
			Optional<Township> townshipOptional = townShipService.findById(dto.getTownshipId());
			Optional<RelationShip> relationshipOptional = relationshipService.findById(dto.getRelationshipID());
			Optional<Customer> customerOptional = customerService.findById(dto.getCustomerID());
			ResidentAddress residentAddress = new ResidentAddress();
			residentAddress.setResidentAddress(dto.getResidentAddress());

			residentAddress.setResidentTownship(townshipOptional.get());

			Name name = new Name();
			name.setFirstName(dto.getFirstName());
			name.setMiddleName(dto.getMiddleName());
			name.setLastName(dto.getLastName());
			
			ContentInfo content = new ContentInfo();
			content.setPhone(dto.getContentInfo()!= null ? dto.getContentInfo().getPhoneOrMoblieNo() : null);

			InsuredPersonBeneficiaries beneficiary = new InsuredPersonBeneficiaries();
			beneficiary.setInitialId(dto.getInitialId());
			beneficiary.setDateOfBirth(dto.getDob());
			beneficiary.setPercentage(dto.getPercentage());
			beneficiary.setIdType(IdType.valueOf(dto.getIdType()));
			beneficiary.setIdNo(dto.getIdNo());
			beneficiary.setGender(Gender.valueOf(dto.getGender()));
			beneficiary.setResidentAddress(residentAddress);
			beneficiary.setName(name);
			beneficiary.setContentInfo(content);
			if(customerOptional.isPresent()) {
				beneficiary.setCustomer(customerOptional.get());
			}
			if (relationshipOptional.isPresent()) {
				beneficiary.setRelationship(relationshipOptional.get());
			}
			String beneficiaryNo = customIdRepo.getNextId("LIFE_BENEFICIARY_ID_GEN", null);
			beneficiary.setBeneficiaryNo(beneficiaryNo);
			beneficiary.setPrefix(insuPersonBenePrefix);
			CommonCreateAndUpateMarks recorder = new CommonCreateAndUpateMarks();
			recorder.setCreatedDate(new Date());
			beneficiary.setRecorder(recorder);
			return beneficiary;
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), e.getMessage());
		}
	}

	private List<LifePolicy> convertSinglePremiumEndowmentLifeProposalToPolicy(List<LifeProposal> singlePremiumEndowmentLifeProposalList) {
		List<LifePolicy> policyList = new ArrayList<>();
		singlePremiumEndowmentLifeProposalList.forEach(proposal -> {
			LifePolicy policy = new LifePolicy(proposal);
			String policyNo = customIdRepo.getNextId(SINGLE_PREMIUM_ENDOWMENT_LIFE_POLICY_NO, null);
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
