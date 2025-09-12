package makara.co.min_pos.controller;

import lombok.RequiredArgsConstructor;
import makara.co.min_pos.base.BaseApi;
import makara.co.min_pos.models.DTO.report.ExpenseReportDTO;
import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.entities.Sale;
import makara.co.min_pos.models.enums.PaymentStatus;
import makara.co.min_pos.models.enums.PurchaseStatus;
import makara.co.min_pos.service.ReportService;
import makara.co.min_pos.service.SaleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("reports")
@RequiredArgsConstructor
public class ReportController {
    private final SaleService saleService;
    private final ReportService service;

    //get reporting sales
    @GetMapping("/report-sale/{status}/{startDate}/{endDate}")
    public BaseApi<?> getSaleReport(
            @PathVariable PaymentStatus status,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate){
        List<Sale> responses = saleService.getSalesByStatusAndDate(startDate,endDate,status);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("time stamp have been found")
                .timeStamp(LocalDateTime.now())
                .data(responses)
                .build();
    }

    //get report purchases
    @GetMapping("report-purchase/{status}//{startDate}/{endDate}")
    public BaseApi<?> getPurchaseReport(
            @PathVariable PurchaseStatus status,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate")LocalDate startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate
            ){
      List<Purchase> responses = service.getPurchaseSold(status,startDate,endDate);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("purchase have been found with sold date")
                .timeStamp(LocalDateTime.now())
                .data(responses)
                .build();
         }


    @GetMapping("expense/{startDate}/{endDate}")
    public BaseApi<?> expenseReport(@DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("startDate") LocalDate startDate,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd") @PathVariable("endDate") LocalDate endDate){

        List<ExpenseReportDTO> expenseReportDTOS = service.getExpenseReport(startDate,endDate);
        return BaseApi.builder()
                .status(true)
                .code(HttpStatus.OK.value())
                .message("Report fetched successfully")
                .timeStamp(LocalDateTime.now())
                .data(expenseReportDTOS)
                .build();
    }

}
