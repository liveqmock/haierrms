package haier.repository.dao.fundmonitor;

import haier.repository.model.fundmonitor.MtActtypeUIBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ActInfoManagerMapper {

    /**
     * zhanrui
     *
     * @return
     */
    @Select("select a.*,a.actno as actnoUI from mt_acttype  a where a.bankcd = #{bankcd}")
    List<MtActtypeUIBean> selectActtypeUIList(@Param("bankcd") String bankcd);

}