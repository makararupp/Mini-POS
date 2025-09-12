package makara.co.min_pos.service;

import makara.co.min_pos.models.DTO.report.ExpenseReportDTO;
import makara.co.min_pos.models.entities.Purchase;
import makara.co.min_pos.models.enums.PurchaseStatus;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<Purchase> getPurchaseSold(PurchaseStatus status,LocalDate startDate, LocalDate endDate);
    List<ExpenseReportDTO> getExpenseReport(LocalDate startDate, LocalDate endDate);
}
