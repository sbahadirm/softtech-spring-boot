package com.softtech.softtechspringboot.app.crd.service;

import com.softtech.softtechspringboot.app.crd.converter.CrdCreditCardActivityMapper;
import com.softtech.softtechspringboot.app.crd.converter.CrdCreditCardMapper;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardActivityDto;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardResponseDto;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardSaveRequestDto;
import com.softtech.softtechspringboot.app.crd.dto.CrdCreditCardSpendDto;
import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCard;
import com.softtech.softtechspringboot.app.crd.entity.CrdCreditCardActivity;
import com.softtech.softtechspringboot.app.crd.enums.CrdCreditCardActivityType;
import com.softtech.softtechspringboot.app.crd.enums.CrdErrorMessage;
import com.softtech.softtechspringboot.app.crd.service.entityservice.CrdCreditCardActivityEntityService;
import com.softtech.softtechspringboot.app.crd.service.entityservice.CrdCreditCardEntityService;
import com.softtech.softtechspringboot.app.gen.enums.GenStatusType;
import com.softtech.softtechspringboot.app.gen.exceptions.GenBusinessException;
import com.softtech.softtechspringboot.app.gen.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
public class CrdCreditCardService {

    private final CrdCreditCardEntityService crdCreditCardEntityService;
    private final CrdCreditCardActivityEntityService crdCreditCardActivityEntityService;

    public CrdCreditCardResponseDto saveCreditCard(CrdCreditCardSaveRequestDto crdCreditCardSaveRequestDto) {

        Long cusCustomerId = crdCreditCardSaveRequestDto.getCusCustomerId();
        BigDecimal earning = crdCreditCardSaveRequestDto.getEarning();
        String cutoffDayStr = crdCreditCardSaveRequestDto.getCutoffDay();

        BigDecimal limit = calculateLimit(earning);
        LocalDate cutoffDateLocal = getCutoffDateLocal(cutoffDayStr);
        Date cutoffDate = DateUtil.convertToDate(cutoffDateLocal);

        Date dueDate = getDueDate(cutoffDateLocal);

        CrdCreditCardResponseDto crdCreditCardResponseDto = createCardAndConvertToCrdCreditCardResponseDto(cusCustomerId, limit, cutoffDate, dueDate);

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

    private CrdCreditCardResponseDto createCardAndConvertToCrdCreditCardResponseDto(Long cusCustomerId, BigDecimal limit, Date cutoffDate, Date dueDate) {

        CrdCreditCard crdCreditCard = createCrdCreditCard(cusCustomerId, limit, cutoffDate, dueDate);

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

    //TODO: implement
    private Long getCvvNo() {
        Long cvvNo = 123L;
        return cvvNo;
    }

    //TODO: implement
    private Long getCardNo() {
        Long cardNo = 1234567890123456L;
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

        BigDecimal currentDebt = crdCreditCard.getCurrentDebt().subtract(amount);
        BigDecimal currentAvailableLimit = crdCreditCard.getAvailableCardLimit().add(amount);

        crdCreditCard.setCurrentDebt(currentDebt);
        crdCreditCard.setAvailableCardLimit(currentAvailableLimit);
        crdCreditCard = crdCreditCardEntityService.save(crdCreditCard);
        return crdCreditCard;
    }
}
