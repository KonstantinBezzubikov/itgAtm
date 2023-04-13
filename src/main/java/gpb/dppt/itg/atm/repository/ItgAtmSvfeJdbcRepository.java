package gpb.dppt.itg.atm.repository;


import gpb.dppt.itg.atm.dto.ItgSvfeCalcFeeAmtDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
@Log4j
@AllArgsConstructor
@NoArgsConstructor
public class ItgAtmSvfeJdbcRepository implements ItgAtmSvfeRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void svistaCalcFee(ItgSvfeCalcFeeAmtDto fee){
        BigInteger calcAcqFeeAmt;
        BigInteger calcIssFeeAmt;

        if (fee.getTransType().equals("TRANSFER")) {
            calcAcqFeeAmt = svistaCalcAcqFee2(fee);}
        else {calcAcqFeeAmt = svistaCalcAcqFee(fee);}

        calcIssFeeAmt = svistaCalcIssFee(fee);

        fee.setFee(calcAcqFeeAmt.add(calcIssFeeAmt));
    }
    @Override
    public BigInteger svistaCalcAcqFee2(ItgSvfeCalcFeeAmtDto fee) {
        BigInteger result = BigInteger.valueOf(0);

        try{
            List<SqlParameter> parameters = Arrays.asList(
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.INTEGER),
                    new SqlOutParameter("status_out", Types.INTEGER));

            Map<String, Object> t = jdbcTemplate.call(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(Connection con) throws SQLException {
                    CallableStatement callableStatement = con.prepareCall("{call get_acq_fee_amount2(?,?,?,?,?,?,?,?,?,?)}");
                    callableStatement.setString(1, "testParanetr1");
                    callableStatement.setString(2, "testParanetr2");
                    callableStatement.setString(3, "testParanetr3");
                    callableStatement.setString(4, "testParanetr4");
                    callableStatement.setInt(5, 2);
                    callableStatement.setInt(6, 2);
                    callableStatement.setInt(7, 2);
                    callableStatement.setString(8, "");
                    callableStatement.setInt(9, 2);
                    callableStatement.registerOutParameter(10, Types.INTEGER);
                    return callableStatement;
                }
            }, parameters);

            result = BigInteger.valueOf((long) Integer.parseInt(t.get("status_out").toString()));
            log.info("->AcqFee {id=" + fee.getTransId() + ",fee=" + result + "}");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public BigInteger svistaCalcAcqFee(ItgSvfeCalcFeeAmtDto fee) {
        BigInteger result = BigInteger.valueOf(0);

        try{
            List<SqlParameter> parameters = Arrays.asList(
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.INTEGER),
                    new SqlParameter(Types.VARCHAR),
                    new SqlParameter(Types.INTEGER),
                    new SqlOutParameter("status_out", Types.INTEGER));

            Map<String, Object> t = jdbcTemplate.call(new CallableStatementCreator() {
                @Override
                public CallableStatement createCallableStatement(Connection con) throws SQLException {
                    CallableStatement callableStatement = con.prepareCall("{call get_acq_fee_amount(?,?,?,?,?,?,?,?,?)}");
                    callableStatement.setString(1, "testParanetr1");
                    callableStatement.setString(2, "testParanetr2");
                    callableStatement.setString(3, "testParanetr3");
                    callableStatement.setInt(4, 2);
                    callableStatement.setInt(5, 2);
                    callableStatement.setInt(6, 2);
                    callableStatement.setString(7, "");
                    callableStatement.setInt(8, 2);
                    callableStatement.registerOutParameter(9, Types.INTEGER);
                    return callableStatement;
                }
            }, parameters);

            result = BigInteger.valueOf((long) Integer.parseInt(t.get("status_out").toString()));
           log.info("->AcqFee: {id=" + fee.getTransId() + ",fee=" + result + "}");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    @Override
    public BigInteger svistaCalcIssFee(ItgSvfeCalcFeeAmtDto fee) {
        BigInteger result = BigInteger.valueOf(0);

        try{
        List<SqlParameter> parameters = Arrays.asList(
                new SqlParameter(Types.VARCHAR),
                new SqlParameter(Types.VARCHAR),
                new SqlParameter(Types.VARCHAR),
                new SqlParameter(Types.VARCHAR),
                new SqlParameter(Types.INTEGER),
                new SqlParameter(Types.INTEGER),
                new SqlParameter(Types.INTEGER),
                new SqlParameter(Types.INTEGER),
                new SqlParameter(Types.INTEGER),
                new SqlParameter(Types.INTEGER),
                new SqlOutParameter("status_out", Types.INTEGER),
                new SqlOutParameter("status_out1", Types.INTEGER));

        Map<String, Object> t = jdbcTemplate.call(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                    CallableStatement callableStatement = con.prepareCall("{call get_iss_fee_amount(?,?,?,?,?,?,?,?,?,?,?,?)}");
                    callableStatement.setString(1, "testParanetr1");
                    callableStatement.setString(2, "testParanetr2");
                    callableStatement.setString(3, "testParanetr3");
                    callableStatement.setString(4, "testParanetr4");
                    callableStatement.setInt(5, 2);
                    callableStatement.setInt(6, 2);
                    callableStatement.setInt(7, 2);
                    callableStatement.setInt(8, 2);
                    callableStatement.setInt(9, 2);
                    callableStatement.setInt(10, 2);
                    callableStatement.registerOutParameter(11, Types.INTEGER);
                callableStatement.registerOutParameter(12, Types.INTEGER);
                return callableStatement;
            }
        }, parameters);

            result = BigInteger.valueOf((long) Integer.parseInt(t.get("status_out").toString()));

            log.info("->IssFee: {id=" + fee.getTransId() + ",fee=" + result + "}");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
