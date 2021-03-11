package org.tat.gginl.api.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.tat.gginl.api.domains.Product;

public class KeyFactorChecker {
	private static String SUM_INSURED = "SUM_INSURED";

	private static String LOADING = "LOADING";

	private static String PUBLIC_LIFE = "PUBLIC_LIFE";

	private static String STUDENT_LIFE = "STUDENT_LIFE";

	private static String GROUP_LIFE = "GROUP_LIFE";

	private static String SNAKE_BITE = "SNAKE_BITE";

	private static String SPORT_MAN = "SPORT_MAN";

	private static String AGE = "AGE";

	private static String TERM = "TERM";

	private static String MEDICALINSURANCE = "MEDICALINSURANCE";

	private static String MEDICAL_AGE = "MEDICAL_AGE";

	private static String INDIVIDUAL_HEALTH_INSURANCE = "INDIVIDUAL_HEALTH_INSURANCE";

	private static String INDIVIDUAL_HEALTH_ADDON1 = "INDIVIDUAL_HEALTH_ADDON1";

	private static String INDIVIDUAL_HEALTH_ADDON2 = "INDIVIDUAL_HEALTH_ADDON2";

	private static String GROUP_HEALTH_INSURANCE = "GROUP_HEALTH_INSURANCE";

	private static String GROUP_HEALTH_ADDON1 = "GROUP_HEALTH_ADDON1";

	private static String GROUP_HEALTH_ADDON2 = "GROUP_HEALTH_ADDON2";

	private static String MICRO_HEALTH = "MICRO_HEALTH";

	private static String INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE = "INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE";

	private static String GROUP_CRITICAL_ILLNESS_INSURANCE = "GROUP_CRITICAL_ILLNESS_INSURANCE";

	// AddOn
	private static String HEALTH_ADDON_1 = "HEALTH_ADDON_1";

	private static String HEALTH_ADDON_2 = "HEALTH_ADDON_2";

	private static String SURRENDER_AGE = "SURRENDER_AGE";

	private static String POLICY_PERIOD = "POLICY_PERIOD";

	private static String PAYMENT_YEAR = "PAYMENT_YEAR";

	private static String LIFE_SURRENDER_PRODUCT = "LIFE_SURRENDER_PRODUCT";

	private static String SHORT_TERM_ENDOWMENT_SURRENDER_PRODUCT = "SHORT_TERM_ENDOWMENT_SURRENDER_PRODUCT";

	private static String LIFE_PAIDUP_PRODUCT = "LIFE_PAIDUP_PRODUCT";

	private static String SHORTTERM_PAIDUP_PRODUCT = "SHORTTERM_PAIDUP_PRODUCT";

	private static String PAYMENTTYPE = "PAYMENTTYPE";

	private static String GENDER = "GENDER";

	private static String FARMER = "FARMER";

	private static String MEDPRO1 = "MEDPRO1";

	// new Product PA
	private static String PERSONAL_ACCIDENT_KYT = "PERSONAL_ACCIDENT_KYT";

	private static String PERSONAL_ACCIDENT_USD = "PERSONAL_ACCIDENT_USD";

	private static String UNDER_100MILES_TRAVEL = "UNDER_100MILES_TRAVEL";

	private static String LOCAL_TRAVEL = "LOCAL_TRAVEL";

	private static String FOREIGN_TRAVEL = "FOREIGN_TRAVEL";

	// ShortTermEndowmentLife
	private static String SHORT_TERM_ENDOWMNENT = "SHORT_TERM_ENDOWMNENT";

	// PaymentType
	private static String LUMPSUM = "LUMPSUM";

	// Old Medical Product
	private static String MEDICAL_INSURANCE = "MEDICAL_INSURANCE";

	private static String MICRO_HEALTH_INSURANCE = "MICRO_HEALTH_INSURANCE";

	private static String SON = "SON";

	private static String DAUGHTER = "DAUGHTER";

	private static Properties idConfig;

	static {
		try {
			idConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("/keyfactor-id-config.properties");
			idConfig.load(in);
			in.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load keyfactor-id-config.properties");
		}
	}

	public static boolean isSumInsured(KeyFactor kf) {
		if (kf.getId().equals(idConfig.getProperty(SUM_INSURED))) {
			return true;
		}
		return false;
	}

	public static boolean isLoading(KeyFactor kf) {
		if (kf.getId().equals(idConfig.getProperty(LOADING))) {
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

	public static boolean isSnakeBite(Product product) {
		if (product.getId().equals(idConfig.getProperty(SNAKE_BITE))) {
			return true;
		}
		return false;
	}

	public static boolean isMedicalInsurance(Product product) {
		if (product.getId().equals(idConfig.getProperty(MEDICALINSURANCE))) {
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

	public static boolean isTerm(KeyFactor kf) {
		if (kf.getId().equals(idConfig.getProperty(TERM))) {
			return true;
		}
		return false;
	}

	public static boolean isAge(KeyFactor kf) {
		if (kf.getId().equals(idConfig.getProperty(AGE))) {
			return true;
		}
		return false;
	}

	public static String getSportmanID() {
		return idConfig.getProperty(SPORT_MAN);
	}

	public static String getSnakeBiteID() {
		return idConfig.getProperty(SNAKE_BITE);
	}

	public static String getPersonalAccidentID() {
		return idConfig.getProperty(PERSONAL_ACCIDENT_KYT);
	}

	public static String getGroupLifeID() {
		return idConfig.getProperty(GROUP_LIFE);
	}

	public static String getPublicLifeID() {
		return idConfig.getProperty(PUBLIC_LIFE);
	}

	public static String getStudentLifeID() {
		return idConfig.getProperty(STUDENT_LIFE);
	}

	public static String getFarmerId() {
		return idConfig.getProperty(FARMER);
	}

	public static boolean isMedicalAge(KeyFactor kf) {
		if (kf.getId().trim().equals(idConfig.getProperty(MEDICAL_AGE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isIndividualHealthInsurance(String productId) {
		if (productId.trim().equals(idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isIndividualHealthAddon1(String productId) {
		if (productId.trim().equals(idConfig.getProperty(INDIVIDUAL_HEALTH_ADDON1).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isIndividualHealthAddon2(String productId) {
		if (productId.trim().equals(idConfig.getProperty(INDIVIDUAL_HEALTH_ADDON2).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupHealthInsurance(String productId) {
		if (productId.trim().equals(idConfig.getProperty(GROUP_HEALTH_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupHealthAddon1(String productId) {
		if (productId.trim().equals(idConfig.getProperty(GROUP_HEALTH_ADDON1).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupHealthAddon2(String productId) {
		if (productId.trim().equals(idConfig.getProperty(GROUP_HEALTH_ADDON2).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isMicroHealth(String productId) {
		if (productId.trim().equals(idConfig.getProperty(MICRO_HEALTH).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isIndividualCritialIllnessInsurance(String productId) {
		if (productId.trim().equals(idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isGroupCriticalIllnessInsurance(String productId) {
		if (productId.trim().equals(idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isHealthAddOn1(String addOnId) {
		if (addOnId.trim().equals(idConfig.getProperty(HEALTH_ADDON_1).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isHealthAddOn2(String addOnId) {
		if (addOnId.trim().equals(idConfig.getProperty(HEALTH_ADDON_2).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isSurrenderAge(KeyFactor kf) {
		if (kf.getId().replaceAll(" ", "").equals(idConfig.getProperty(SURRENDER_AGE).replaceAll(" ", ""))) {
			return true;
		}
		return false;
	}

	public static boolean isPaymentYear(KeyFactor kf) {
		if (kf.getId().replaceAll(" ", "").equals(idConfig.getProperty(PAYMENT_YEAR).replaceAll(" ", ""))) {
			return true;
		}
		return false;
	}

	public static boolean isPolicyPeriod(KeyFactor kf) {
		if (kf.getId().replaceAll(" ", "").equals(idConfig.getProperty(POLICY_PERIOD).replaceAll(" ", ""))) {
			return true;
		}
		return false;
	}

	public static String getLifeSurrenderProductId() {
		return idConfig.getProperty(LIFE_SURRENDER_PRODUCT);
	}

	public static String getShortTermEndowmentSurrenderProductId() {
		return idConfig.getProperty(SHORT_TERM_ENDOWMENT_SURRENDER_PRODUCT);
	}

	public static String getLifePaidUpProductId() {
		return idConfig.getProperty(LIFE_PAIDUP_PRODUCT);
	}

	public static String getShortTermPaidUpProductId() {
		return idConfig.getProperty(SHORTTERM_PAIDUP_PRODUCT);
	}

	public static String getIndividualCriticalIllnessProductId() {
		return idConfig.getProperty(INDIVIDUAL_CRITICAL_ILLNESS_INSURANCE);
	}

	public static String getGroupCriticalIllnessProductId() {
		return idConfig.getProperty(GROUP_CRITICAL_ILLNESS_INSURANCE);
	}

	public static String getIndividualHealthProductId() {
		return idConfig.getProperty(INDIVIDUAL_HEALTH_INSURANCE);
	}

	public static String getGroupHealthProductId() {
		return idConfig.getProperty(GROUP_HEALTH_INSURANCE);
	}

	public static String getMicroHealthProductId() {
		return idConfig.getProperty(MICRO_HEALTH_INSURANCE);
	}

	public static String getMedicalProductId() {
		return idConfig.getProperty(MEDICAL_INSURANCE);
	}

	public static String getSonfromRelationShipTable() {
		return idConfig.getProperty(SON);
	}

	public static String getDaughterfromRelationShipTable() {
		return idConfig.getProperty(DAUGHTER);
	}

	public static boolean isPaymentType(KeyFactor kf) {
		if (kf.getId().equals(idConfig.getProperty(PAYMENTTYPE).replaceAll(" ", ""))) {
			return true;
		}
		return false;
	}

	public static boolean isGender(KeyFactor kf) {
		if (kf.getId().equals(idConfig.getProperty(GENDER))) {
			return true;
		}
		return false;
	}

	public static boolean isFarmer(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(FARMER).trim())) {
			return true;
		}
		return false;
	}

	public static String getMEDPRO1ID() {
		return idConfig.getProperty(MEDPRO1);
	}

	public static boolean isPersonalAccident(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(PERSONAL_ACCIDENT_KYT).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isPersonalAccidentUSD(Product product) {
		if (product.getId().trim().equals(idConfig.getProperty(PERSONAL_ACCIDENT_USD).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isUnder100MilesTravel(String productId) {
		if (productId.trim().equals(idConfig.getProperty(UNDER_100MILES_TRAVEL).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isLocalTravel(String productId) {
		if (productId.trim().equals(idConfig.getProperty(LOCAL_TRAVEL).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isOverseaTravel(String productId) {
		if (productId.trim().equals(idConfig.getProperty(FOREIGN_TRAVEL).trim())) {
			return true;
		}
		return false;
	}

	public static boolean isShortTermEndowment(String productId) {
		if (productId.trim().equals(idConfig.getProperty(SHORT_TERM_ENDOWMNENT).trim())) {
			return true;
		}
		return false;
	}

	public static String getShortTermEndowmentID() {
		return idConfig.getProperty(SHORT_TERM_ENDOWMNENT);
	}

	public static String getLumpsumId() {
		return idConfig.getProperty(LUMPSUM);
	}

	public static boolean isStudentLife(String productId) {
		if (productId.trim().equals(idConfig.getProperty(STUDENT_LIFE).trim())) {
			return true;
		}
		return false;
	}
}
