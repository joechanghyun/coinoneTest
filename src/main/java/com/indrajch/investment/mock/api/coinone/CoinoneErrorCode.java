package com.indrajch.investment.mock.api.coinone;

public enum CoinoneErrorCode {
	BlockedUserAccess(4, "Blocked user access"), //
	AccessTokenIsMiissing(11, "Access tokenIs missing"), //
	InvalidAccessToken(12, "Invalid access token"), //
	InvalidAPIPermission(40, "Invalid API permission"), //
	AuthenticateError(50, "Authenticate error"), //
	InvalidAPI(51, "Invalid API"), //
	DeprecatedAPI(52, "Deprecated API"), //
	TwoFactorAuthFail(53, "Two Factor Auth Fail"), //
	SessionExpired(100, "Session expired"), //
	InvalidFormat(101, "Invalid format"), //
	IDIsNotExist(102, "ID is not exist"), //
	LackOfBalance(103, "Lack of Balance"), //
	OrderIdIsNotExist(104, "Order id is not exist"), //
	PriceIsNotCorrect(105, "Price is not correct"), //
	LockingError(106, "Locking error"), //
	ParameterError(107, "Parameter error"), //
	OrderIdIsNotExist2(111, "Order id is not exist"), //
	CancelFailed(112, "Cancel failed"), //
	QuantityIsToLow(113, "Quantity is too low(ETH, ETC > 0.01)"), //
	PayloadIsMissing(120, "V2 API payload is missing"), //
	SignatureIsMissing(121, "V2 API signature is missing"), //
	NonceIsMissing(122, "V2 API nonce is missing"), //
	SignatureIsNotCorrect(123, "V2 API signature is not correct"), //
	NonceValueMustBeAPositiveInteger(130, "V2 API Nonce value must be a positive integer"), //
	NonceIsMustBeBiggerThenLastNonce(131, "V2 API Nonce is must be bigger then last nonce"), //
	BodyIsCorrupted(132, "V2 API body is corrupted"), //
	TooManyLimitOrders(141, "Too many limit orders"), //
	AccessTokenIsNotAcceptable(150, "It's V1 API. V2 Access token is not acceptable"), //
	AccessTokenIsNotAcceptable2(151, "It's V2 API. V1 Access token is not acceptable"), //
	WalletError(200, "Wallet Error"), //
	LimitationError(202, "Limitation error"), //
	LimitationError2(210, "Limitation error"), //
	LimitationError3(220, "Limitation error"), //
	LimitationError4(221, "Limitation error"), //
	MobileAuthError5(310, "Mobile auth error"), //
	NeedMobileAuth(311, "Need mobile auth"), //
	NameIsNotCorrect(312, "Name is not correct"), //
	PhoneNumberError2(330, "Phone number error"), //
	PageNotFoundError(404, "Page not found error"), //
	ServerError(405, "Server error"), //
	LockingError2(444, "Locking error"), //
	EmailError(500, "Email error"), //
	EmailError2(501, "Email error"), //
	MobileAuthError(777, "Mobile auth error"), //
	PhoneNumberError(778, "Phone number error"), //
	AddressError(779, "Address error"), //
	AppNotFound(1202, "App not found"), //
	AlreadyRegistered(1203, "Already registered"), //
	InvalidAccess(1204, "Invalid access"), //
	APIKeyError(1205, "API Key error"), //
	UserNotFound(1206, "User not found"), //
	UserNotFound2(1207, "User not found"), //
	UserNotFound3(1208, "User not found"), //
	UserNotFound4(1209, "User not found"), //
	;

	private int code;
	private String description;

	private CoinoneErrorCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public static CoinoneErrorCode findOf(String code) {
		try {
			int intCode = Integer.parseInt(code);
			for (CoinoneErrorCode errorCode : CoinoneErrorCode.values()) {
				if (errorCode.getCode() == intCode) {
					return errorCode;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}
}
