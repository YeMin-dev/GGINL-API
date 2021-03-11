package org.tat.gginl.api.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.tat.gginl.api.common.emumdata.PolicyReferenceType;
import org.tat.gginl.api.domains.Product;
import org.tat.gginl.api.exception.ErrorCode;
import org.tat.gginl.api.exception.SystemException;



public class ProductIDConfig {
	private static String PUBLIC_LIFE = "PUBLIC_LIFE";
	private static String GROUP_LIFE = "GROUP_LIFE";
	private static String PERSONAL_ACCIDENT_KYT = "PERSONAL_ACCIDENT_KYT";
	private static String PERSONAL_ACCIDENT_USD = "PERSONAL_ACCIDENT_USD";
	private static String SNAKE_BITE = "SNAKE_BITE";
	private static String SPORT_MAN = "SPORT_MAN";

	private static String YANGON_BRANCH = "YANGON_BRANCH";
	private static String MEDICAL_INSURANCE = "MEDICAL_INSURANCE";
	private static String FARMER = "FARMER";
	private static String SHORT_TERM_ENDOWMNENT = "SHORT_TERM_ENDOWMNENT";
	private static String MED_ADDON1 = "MED_ADDON1";
	private static String MED_ADDON2 = "MED_ADDON2";
	private static String MED_ADDON3 = "MED_ADDON3";
	private static String MED_PROD_ADDON1 = "MED_PROD_ADDON1";
	private static String MED_PROD_ADDON2 = "MED_PROD_ADDON2";
	private static String MED_PROD_ADDON3 = "MED_PROD_ADDON3";

	private static String LOCAL_TRAVEL = "LOCAL_TRAVEL";
	private static String UNDER_100MILES_TRAVEL = "UNDER_100MILES_TRAVEL";
	private static String FOREIGN_TRAVEL = "FOREIGN_TRAVEL";

	private static String MOBILE_ACCRUED = "MOBILE_ACCRUED";

	// New Medical Product

	private static String HEALTH_ADDON_1 = "HEALTH_ADDON_1";
	private static String HEALTH_ADDON_2 = "HEALTH_ADDON_2";

	private static String INDIVIDUAL_HEALTH_INSURANCE = "INDIVIDUAL_HEALTH_INSURANCE";
	private static String INDIVIDUAL_HEALTH_ADDON_1_PRODUCT = "INDIVIDUAL_HEALTH_ADDON_1_PRODUCT";
	private static String INDIVIDUAL_HEALTH_ADDON_2_PRODUCT = "INDIVIDUAL_HEALTH_ADDON_2_PRODUCT";

	private static String GROUP_HEALTH_INSURANCE = "GROUP_HEALTH_INSURANCE";
	private static String GROUP_HEALTH_ADDON_1_PRODUCT = "GROUP_HEALTH_ADDON_1_PRODUCT";
	private static String GROUP_HEALTH_ADDON_2_PRODUCT = "GROUP_HEALTH_ADDON_2_PRODUCT";

	private static String MICRO_HEALTH_INSURANCE = "MICRO_HEALTH_INSURANCE";
	private static String INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE = "INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE";
	private static String GROUP_CRITICAL_ILLNESS_INSURANCE = "GROUP_CRITICAL_ILLNESS_INSURANCE";
	private static String TRAVEL_PROPOSAL = "TRAVEL_PROPOSAL";
	private static String STUDENT_LIFE = "STUDENT_LIFE";

	private static String PUBLIC_TERM_LIFE = "PUBLIC_TERM_LIFE";
	private static String SINGLE_PREMIUM_ENDOWMENT_LIFE = "SINGLE_PREMIUM_ENDOWMENT_LIFE";
	private static Properties idConfig;

	static {
		try {
			idConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("keyfactor-id-config.properties");
			idConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		//	throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load keyfactor-id-config.properties");
		}
	}

	public static String getMedAddOn1() {
		return idConfig.getProperty(MED_ADDON1);
	}

	public static String getMedAddOn2() {
		return idConfig.getProperty(MED_ADDON2);
	}

	public static String getMedAddOn3() {
		return idConfig.getProperty(MED_ADDON3);
	}

	public static String getPublicLifeId() {
		return idConfig.getProperty(PUBLIC_LIFE);
	}

	public static String getGroupLifeId() {
		return idConfig.getProperty(GROUP_LIFE);
	}

	public static String getFarmerId() {
		return idConfig.getProperty(FARMER);
	}

	public static String getShortEndowLifeId() {
		return idConfig.getProperty(SHORT_TERM_ENDOWMNENT);
	}

	public static String getStudentLifeId() {
		return idConfig.getProperty(STUDENT_LIFE);
	}

	public static String getPublicTermLifeId() {
		return idConfig.getProperty(PUBLIC_TERM_LIFE);
	}

	public static String getSinglePremiumEdowmentLifeId() {
		return idConfig.getProperty(SINGLE_PREMIUM_ENDOWMENT_LIFE);
	}

	public static String getPersonalAccidentMMKId() {
		return idConfig.getProperty(PERSONAL_ACCIDENT_KYT);
	}

	public static String getPersonalAccidentUSDId() {
		return idConfig.getProperty(PERSONAL_ACCIDENT_USD);
	}

	public static String getSnakeBikeId() {
		return idConfig.getProperty(SNAKE_BITE);
	}

	public static String getSportManId() {
		return idConfig.getProperty(SPORT_MAN);
	}

	public static String getMedProdAddOn1() {
		return idConfig.getProperty(MED_PROD_ADDON1);
	}

	public static String getMedProdAddOn2() {
		return idConfig.getProperty(MED_PROD_ADDON2);
	}

	public static String getMedProdAddOn3() {
		return idConfig.getProperty(MED_PROD_ADDON3);
	}

	public static String getYangonBranchId() {
		return idConfig.getProperty(YANGON_BRANCH);
	}

	public static boolean isSnakeBite(Product product) {
		if (product.getId().equals(idConfig.getProperty(SNAKE_BITE))) {
			return true;
		}
		return false;
	}

	public static boolean isPublicLife(Product product) {
		if (product.getId().equals(idConfig.getProperty(PUBLIC_LIFE))) {
			return true;
		}
		return false;
	}

	public static boolean isGroupLife(Product product) {
		if (product.getId().equals(idConfig.getProperty(GROUP_LIFE))) {
			return true;
		}
		return false;
	}

	public static boolean isSportMan(Product product) {
		if (product.getId().equals(idConfig.getProperty(SPORT_MAN))) {
			return true;
		}
		return false;
	}

	public static boolean isShortEndowmentLife(Product product) {
		if (product.getId().equals(idConfig.getProperty(SHORT_TERM_ENDOWMNENT))) {
			return true;
		}
		return false;
	}

	public static boolean isStudentLife(Product product) {
		if (product.getId().equals(idConfig.getProperty(STUDENT_LIFE))) {
			return true;
		}
		return false;
	}

	public static String getMedicalProductId() {
		return idConfig.getProperty(MEDICAL_INSURANCE).trim();
	}

	public static String getMicroHealthInsurance() {
		return idConfig.getProperty(MICRO_HEALTH_INSURANCE).trim();
	}

	public static boolean isFarmer(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(FARMER).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPublicTermLife(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(PUBLIC_TERM_LIFE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSinglePremiumEndowmentLife(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(SINGLE_PREMIUM_ENDOWMENT_LIFE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccidentKYT(Product product) {
		if (product.getId().trim().endsWith(idConfig.getProperty(PERSONAL_ACCIDENT_KYT).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccidentUSD(Product product) {
		if (product.getId().trim().endsWith(idConfig.getProperty(PERSONAL_ACCIDENT_USD).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isLocalTravelInsurance(Product product) {
		if (product.getId().trim().endsWith(idConfig.getProperty(LOCAL_TRAVEL).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isUnder100MileTravelInsurance(Product product) {
		if (product.getId().trim().endsWith(idConfig.getProperty(UNDER_100MILES_TRAVEL).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isForeignTravelInsurance(Product product) {
		if (product.getId().trim().endsWith(idConfig.getProperty(FOREIGN_TRAVEL).trim())) {
			return true;
		}
		return false;
	}

	public static String getMobileAccuredBankId() {
		return idConfig.getProperty(MOBILE_ACCRUED);
	}

	public static boolean isMedicalInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(MEDICAL_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isIndividualHealthInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupHealthInsurancae(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(GROUP_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isMicroHealthInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(MICRO_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupCriticalIllnessInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isIndividualCriticalIllnessInsurance(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static String getTravelInsuranceId() {
		return idConfig.getProperty(TRAVEL_PROPOSAL).trim();
	}

	public static String getPersonTravelId() {
		return idConfig.getProperty(LOCAL_TRAVEL).trim();

	}

	public static String getPersonTravelFoeign() {
		return idConfig.getProperty(FOREIGN_TRAVEL).trim();
	}

	public static String getPersonTravelUnder100Id() {
		return idConfig.getProperty(UNDER_100MILES_TRAVEL).trim();
	}

	public static String getIndividualHealthInsuranceId() {
		return idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE).trim();
	}

	public static String getGroupHealthInsuranceId() {
		return idConfig.getProperty(GROUP_HEALTH_INSURANCE).trim();
	}

	public static String getIndividualCriticalIllness_Id() {
		return idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE).trim();
	}

	public static String getGroupCriticalIllness_Id() {
		return idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE).trim();
	}

	public static String getHealthAddOn1() {
		return idConfig.getProperty(HEALTH_ADDON_1);
	}

	public static String getHealthAddOn2() {
		return idConfig.getProperty(HEALTH_ADDON_2);
	}

	public static String getIndividualHealthAddOn1ProductID() {
		return idConfig.getProperty(INDIVIDUAL_HEALTH_ADDON_1_PRODUCT);
	}

	public static String getIndividualHealthAddOn2ProductID() {
		return idConfig.getProperty(INDIVIDUAL_HEALTH_ADDON_2_PRODUCT);
	}

	public static String getGroupHealthAddOn1ProductID() {
		return idConfig.getProperty(GROUP_HEALTH_ADDON_1_PRODUCT);
	}

	public static String getGroupHealthAddOn2ProductID() {
		return idConfig.getProperty(GROUP_HEALTH_ADDON_2_PRODUCT);
	}

	public static String getMedicalInsurance() {
		return idConfig.getProperty(MEDICAL_INSURANCE);
	}

	public static String getUnder100MilesTravelProductID() {
		return idConfig.getProperty(UNDER_100MILES_TRAVEL);
	}

	public static List<String> getIdByReferenceType(ReferenceType referenceType) {
		switch (referenceType) {
			case CRITICAL_ILLNESS_POLICY:
			case CRITICAL_ILLNESS_PROPOSAL:
				return Arrays.asList(idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE), idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE));
			case HEALTH_POLICY:
			case HEALTH_PROPOSAL:
				return Arrays.asList(idConfig.getProperty(GROUP_HEALTH_INSURANCE), idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE));
			case MICRO_HEALTH_POLICY:
			case MICRO_HEALTH_PROPOSAL:
				return Arrays.asList(idConfig.getProperty(MICRO_HEALTH_INSURANCE));
			case LIFE_POLICY:
			case LIFE_PROPOSAL:
				return Arrays.asList(idConfig.getProperty(PUBLIC_LIFE), idConfig.getProperty(GROUP_LIFE));
			case MEDICAL_POLICY:
			case MEDICAL_PROPOSAL:
				return Arrays.asList(idConfig.getProperty(MEDICAL_INSURANCE));
			case SHORT_ENDOWMENT_LIFE_POLICY:
			case SHORT_ENDOWMENT_LIFE_PROPOSAL:
				return Arrays.asList(idConfig.getProperty(SHORT_TERM_ENDOWMNENT));
			case STUDENT_LIFE_PROPOSAL:
			case STUDENT_LIFE_POLICY:
				return Arrays.asList(idConfig.getProperty(STUDENT_LIFE));
			default:
				return null;
		}
	}

	public static PolicyReferenceType getMedicalPolicyReferenceType(Product product) {
		if (isMedicalInsurance(product)) {
			return PolicyReferenceType.MEDICAL_POLICY;
		} else if (isGroupHealthInsurancae(product) || isIndividualHealthInsurance(product)) {
			return PolicyReferenceType.HEALTH_POLICY;
		} else if (isMicroHealthInsurance(product)) {
			return PolicyReferenceType.MICRO_HEALTH_POLICY;
		} else if (isGroupCriticalIllnessInsurance(product) || isIndividualCriticalIllnessInsurance(product)) {
			return PolicyReferenceType.CRITICAL_ILLNESS_POLICY;
		}
		return null;
	}

	public static boolean isLifeProduct(String productId) {
		return (productId.equals(getPublicLifeId()) || productId.equals(getGroupLifeId()) || productId.equals(getFarmerId()) || productId.equals(getSportManId())
				|| productId.equals(getShortEndowLifeId()) || productId.equals(getSnakeBikeId()) || productId.equals(getPublicTermLifeId()));
	}

	public static boolean isMedicalProduct(String productId) {
		return (productId.equals(getMicroHealthInsurance()) || productId.equals(getIndividualCriticalIllness_Id()) || productId.equals(getGroupCriticalIllness_Id())
				|| productId.equals(getIndividualHealthInsuranceId()) || productId.equals(getGroupHealthInsuranceId()));
	}

	public static boolean isTravelProduct(String productId) {
		return productId.equals(getTravelInsuranceId());
	}

	public static boolean isParsonalTravelProduct(String productId) {
		return productId.equals(getPersonTravelId());
	}

	public static boolean isForeignTravel(String productId) {
		return productId.equals(getPersonTravelFoeign());
	}

	public static boolean isUnder100Travel(String productId) {
		return productId.equals(getPersonTravelUnder100Id());
	}

	public static boolean isGroupFarmer(String productId) {
		return productId.equals(getFarmerId());
	}

	

}
