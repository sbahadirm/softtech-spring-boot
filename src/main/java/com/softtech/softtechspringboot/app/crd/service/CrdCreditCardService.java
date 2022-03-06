package com.softtech.softtechspringboot.app.crd.service;

import com.softtech.softtechspringboot.app.crd.converter.CrdCreditCardActivityMapper;
import com.softtech.softtechspringboot.app.crd.converter.CrdCreditCardMapper;
import com.softtech.softtechspringboot.app.crd.dto.*;
import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCard;
import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCardActivity;
import com.softtech.softtechspringboot.app.crd.enums.CrdCreditCardActivityType;
import com.softtech.softtechspringboot.app.crd.enums.CrdErrorMessage;
import com.softtech.softtechspringboot.app.crd.service.entityservice.CrdCreditCardActivityEntityService;
import com.softtech.softtechspringboot.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.softtech.softtechspringboot.app.gen.enums.GenStatusType;
import com.softtech.softtechspringboot.app.gen.exceptions.GenBusinessException;
import com.softtech.softtechspringboot.app.gen.util.DateUtil;
import com.softtech.softtechspringboot.app.gen.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CrdCreditCardService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;
    private final CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;

    public CrdCreditCardResponseDto saveCreditCard(CrdCreditCardSaveRequestDto crdCreditCardSaveRequestDto) {

        BigDecimal earning = crdCreditCardSaveRequestDto.getEarning();
        String cutoffDayStr = crdCreditCardSaveRequestDto.getCutoffDay();

        BigDecimal limit = calculateLimit(earning);
        LocalDate cutoffDateLocal = getCutoffDateLocal(cutoffDayStr);
        Date cutoffDate = DateUtil.convertToDate(cutoffDateLocal);

        Date dueDate = getDueDate(cutoffDateLocal);

        CrdCreditCardResponseDto crdCreditCardResponseDto = createCardAndConvertToCrdCreditCardResponseDto(limit, cutoffDate, dueDate);

        return crdCreditCardResponseDto;
    }

    public List<CrdCreditCardResponseDto> findAll() {

        List<CrdCreditCard> crdCreditCardList = crdCreditCardEntityService.findAllActiveCreditCardList();

        List<CrdCreditCardResponseDto> result = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardResponseDtoList(crdCreditCardList);

        return result;
    }

    public CrdCreditCardResponseDto findById(Long id) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);

        CrdCreditCardResponseDto result = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardResponseDto(crdCreditCard);

        return result;
    }

    private CrdCreditCardResponseDto createCardAndConvertToCrdCreditCardResponseDto(BigDecimal limit, Date cutoffDate, Date dueDate) {

        Long currentCustomerId = crdCreditCardActivityEntityService.getCurrentCustomerId();
        CrdCreditCard crdCreditCard = createCrdCreditCard(currentCustomerId, limit, cutoffDate, dueDate);
        crdCreditCard.setCusCustomerId(currentCustomerId);

        CrdCreditCardResponseDto crdCreditCardResponseDto = CrdCreditCardMapper.INSTANCE.convertToCrdCreditCardResponseDto(crdCreditCard);

        return crdCreditCardResponseDto;
    }

    private CrdCreditCard createCrdCreditCard(Long cusCustomerId, BigDecimal limit, Date cutoffDate, Date dueDate) {

        Date expireDate = getExpireDate();
        Long cardNo = getCardNo();
        Long cvvNo = getCvvNo();

        CrdCreditCard crdCreditCard = new CrdCreditCard();
        crdCreditCard.setCusCustomerId(cusCustomerId);
        crdCreditCard.setCutoffDate(cutoffDate);
        crdCreditCard.setDueDate(dueDate);
        crdCreditCard.setExpireDate(expireDate);
        crdCreditCard.setCardNo(cardNo);
        crdCreditCard.setCvvNo(cvvNo);
        crdCreditCard.setTotalLimit(limit);
        crdCreditCard.setAvailableCardLimit(limit);
        crdCreditCard.setMinimumPaymentAmount(BigDecimal.ZERO);
        crdCreditCard.setCurrentDebt(BigDecimal.ZERO);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    private BigDecimal calculateLimit(BigDecimal earning) {
        BigDecimal limit = earning.multiply(BigDecimal.valueOf(3));
        return limit;
    }

    private Date getExpireDate() {
        LocalDate expireDateLocal = getExpireDateLocal();
        return DateUtil.convertToDate(expireDateLocal);
    }

    private LocalDate getExpireDateLocal() {
        LocalDate expireDateLocal = LocalDate.now().plusYears(3);
        return expireDateLocal;
    }

    private Date getDueDate(LocalDate cutoffDateLocal) {
        LocalDate dueDateLocal = getDueDateLocal(cutoffDateLocal);
        Date dueDate = DateUtil.convertToDate(dueDateLocal);
        return dueDate;
    }

    private LocalDate getDueDateLocal(LocalDate cutoffDateLocal) {
        LocalDate dueDateLocal = cutoffDateLocal.plusDays(10);
        return dueDateLocal;
    }

    private LocalDate getCutoffDateLocal(String cutoffDayStr) {
        int currentYear = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();
        Month nextMonth = Month.of(currentMonth).plus(1);

        Integer cutoffDay = getCutoffDay(cutoffDayStr);
        LocalDate cutoffDateLocal = LocalDate.of(currentYear, nextMonth, cutoffDay);
        return cutoffDateLocal;
    }

    private Integer getCutoffDay(String cutoffDayStr) {
        if (!StringUtils.hasText(cutoffDayStr)){
            cutoffDayStr = "1";
        }

        Integer cutoffDay = Integer.valueOf(cutoffDayStr);
        return cutoffDay;
    }

    private Long getCvvNo() {
        Long cvvNo = StringUtil.getRandomNumber(3);
        return cvvNo;
    }

    private Long getCardNo() {
        Long cardNo = StringUtil.getRandomNumber(16);
        return cardNo;
    }

    public void cancel(Long cardId) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(cardId);

        crdCreditCard.setStatusType(GenStatusType.PASSIVE);
        crdCreditCard.setCancelDate(new Date());

        crdCreditCardEntityService.save(crdCreditCard);
    }

    public CrdCreditCardActivityDto spend(CrdCreditCardSpendDto crdCreditCardSpendDto) {

        BigDecimal amount = crdCreditCardSpendDto.getAmount();
        String description = crdCreditCardSpendDto.getDescription();

        CrdCreditCard crdCreditCard = getCrdCreditCard(crdCreditCardSpendDto);

        validateCreditCard(crdCreditCard);

        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().add(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().subtract(amount);

        validateCardLimit(currentAvailableLimit);

        crdCreditCard = updateCreditCardForSpend(crdCreditCard, currentDebt, currentAvailableLimit);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardActivityForSpend(amount, description, crdCreditCard);

        CrdCreditCardActivityDto result = CrdCreditCardActivityMapper.INSTANCE.convertToCrdCreditCardActivityDto(crdCreditCardActivity);

        return result;
    }

    private CrdCreditCard updateCreditCardForSpend(CrdCreditCard crdCreditCard, BigDecimal currentDebt, BigDecimal currentAvailableLimit) {
        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);

        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);

        return crdCreditCard;
    }

    private CrdCreditCard getCrdCreditCard(CrdCreditCardSpendDto crdCreditCardSpendDto) {
        Long cardNo = crdCreditCardSpendDto.getCardNo();
        Long cvvNo = crdCreditCardSpendDto.getCvvNo();
        Date expireDate = crdCreditCardSpendDto.getExpireDate();
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.findByCardNoAndCvvNoAndExpireDate(cardNo, cvvNo, expireDate);
        return crdCreditCard;
    }

    private void validateCardLimit(BigDecimal currentAvailableLimit) {
        if (currentAvailableLimit.compareTo(BigDecimal.ZERO) < 0){
            throw new GenBusinessException(CrdErrorMessage.INSUFFICIENT_CREDIT_CARD_LIMIT);
        }
    }

    private void validateCreditCard(CrdCreditCard crdCreditCard) {
        if (crdCreditCard == null){
            throw new GenBusinessException(CrdErrorMessage.INVALID_CREDIT_CARD);
        }

        if (crdCreditCard.getExpireDate().before(new Date())){
            throw new GenBusinessException(CrdErrorMessage.CREDIT_CARD_EXPIRED);
        }
    }

    private CrdCreditCardActivity createCrdCreditCardActivityForSpend(BigDecimal amount, String description, CrdCreditCard crdCreditCard) {
        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription(description);
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.SPEND);
        crdCreditCardActivity.setTransactionDate(new Date());

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    public CrdCreditCardActivityDto refund(Long activityId) {

        CrdCreditCardActivity oldCrdCreditCardActivity = crdCreditCardActivityEntityService.getByIdWithControl(activityId);
        BigDecimal amount = oldCrdCreditCardActivity.getAmount();

        CrdCreditCard crdCreditCard = updateCreditCardForRefund(oldCrdCreditCardActivity, amount);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardActivityForRefund(oldCrdCreditCardActivity, amount, crdCreditCard);

        CrdCreditCardActivityDto result = CrdCreditCardActivityMapper.INSTANCE.convertToCrdCreditCardActivityDto(crdCreditCardActivity);

        return result;
    }

    private CrdCreditCardActivity createCrdCreditCardActivityForRefund(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount, CrdCreditCard crdCreditCard) {
        String description = "REFUND -> " + oldCrdCreditCardActivity.getDescription();

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCard.getId());
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription(description);
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.REFUND);
        crdCreditCardActivity.setTransactionDate(new Date());

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    private CrdCreditCard updateCreditCardForRefund(CrdCreditCardActivity oldCrdCreditCardActivity, BigDecimal amount) {
        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(oldCrdCreditCardActivity.getCrdCreditCardId());

        crdCreditCard = addLimitToCard(crdCreditCard, amount);
        return crdCreditCard;
    }

    private CrdCreditCard addLimitToCard(CrdCreditCard crdCreditCard, BigDecimal amount) {
        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().subtract(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().add(amount);

        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);
        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }

    public CrdCreditCardActivityDto payment(CrdCreditCardPaymentDto crdCreditCardPaymentDto) {

        Long crdCreditCardId = crdCreditCardPaymentDto.getCrdCreditCardId();
        BigDecimal amount = crdCreditCardPaymentDto.getAmount();

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(crdCreditCardId);

        addLimitToCard(crdCreditCard, amount);

        CrdCreditCardActivity crdCreditCardActivity = createCrdCreditCardActivityForPayment(crdCreditCardId, amount);

        CrdCreditCardActivityDto result = CrdCreditCardActivityMapper.INSTANCE.convertToCrdCreditCardActivityDto(crdCreditCardActivity);

        return result;
    }

    private CrdCreditCardActivity createCrdCreditCardActivityForPayment(Long crdCreditCardId, BigDecimal amount) {

        CrdCreditCardActivity crdCreditCardActivity = new CrdCreditCardActivity();
        crdCreditCardActivity.setCrdCreditCardId(crdCreditCardId);
        crdCreditCardActivity.setAmount(amount);
        crdCreditCardActivity.setDescription("PAYMENT");
        crdCreditCardActivity.setCardActivityType(CrdCreditCardActivityType.PAYMENT);
        crdCreditCardActivity.setTransactionDate(new Date());

        crdCreditCardActivity = crdCreditCardActivityEntityService.save(crdCreditCardActivity);
        return crdCreditCardActivity;
    }

    public List<CrdCreditCardActivityDto> findAllActivities(Long id, Date startDate, Date endDate) {

        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService
                .findAllByCrdCreditCardIdAndTransactionDateBetween(id, startDate, endDate);

        List<CrdCreditCardActivityDto> result = CrdCreditCardActivityMapper.INSTANCE.convertToCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        return result;
    }

    public CrdCreditCardDetails statement(Long id) {

        CrdCreditCard crdCreditCard = crdCreditCardEntityService.getByIdWithControl(id);
        Date termEndDate = crdCreditCard.getCutoffDate();
        Long crdCreditCardId = crdCreditCard.getId();

        LocalDate cutoffDateLocal = DateUtil.convertToLocalDate(termEndDate);

        LocalDate termStartDateLocal = cutoffDateLocal.minusMonths(1);
        Date termStartDate = DateUtil.convertToDate(termStartDateLocal);

        CrdCreditCardDetails creditCardDetails = crdCreditCardEntityService.getCreditCardDetails(crdCreditCardId);

        List<CrdCreditCardActivity> crdCreditCardActivityList = crdCreditCardActivityEntityService
                .findAllByCrdCreditCardIdAndTransactionDateBetween(crdCreditCardId, termStartDate, termEndDate);

        List<CrdCreditCardActivityDto> crdCreditCardActivityDtoList = CrdCreditCardActivityMapper.INSTANCE
                .convertToCrdCreditCardActivityDtoList(crdCreditCardActivityList);

        creditCardDetails.setCrdCreditCardActivityDtoList(crdCreditCardActivityDtoList);

        return creditCardDetails;
    }

}
