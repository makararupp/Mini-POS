package makara.co.min_pos.repository;

import makara.co.min_pos.models.entities.ExchangeRate;
import makara.co.min_pos.models.entities.GeneralSetting;
import makara.co.min_pos.models.response.GeneralSettingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneralSettingRepository extends JpaRepository<GeneralSetting,Long> {
    Optional<GeneralSetting> findByIdAndIsDeletedFalse(Long id);
    List<GeneralSetting> findByIsDeletedIsFalseOrderByIdDesc();
    Page<GeneralSetting> findByIsDeletedIsFalseOrderByIdDesc(Pageable pageable);

}
