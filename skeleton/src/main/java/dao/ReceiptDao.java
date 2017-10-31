package dao;

import generated.tables.records.CombinationRecord;
import generated.tables.records.ReceiptsRecord;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static generated.Tables.RECEIPTS;
import static generated.Tables.TAGS;

public class ReceiptDao {
    DSLContext dsl;

    public ReceiptDao(Configuration jooqConfig) {
        this.dsl = DSL.using(jooqConfig);
    }

    public int insert(String merchantName, BigDecimal amount) {
        ReceiptsRecord receiptsRecord = dsl
                .insertInto(RECEIPTS, RECEIPTS.MERCHANT, RECEIPTS.AMOUNT)
                .values(merchantName, amount)
                .returning(RECEIPTS.ID)
                .fetchOne();

        checkState(receiptsRecord != null && receiptsRecord.getId() != null, "Insert failed");

        return receiptsRecord.getId();
    }

    public List<CombinationRecord> getAllReceipts() {
        //return dsl.selectFrom(RECEIPTS).fetch();

        List<CombinationRecord> list = new ArrayList<>();
        Result<Record5<Integer, Time, String, BigDecimal, String>> res = dsl.select(RECEIPTS.ID, RECEIPTS.UPLOADED,
                RECEIPTS.MERCHANT, RECEIPTS.AMOUNT, TAGS.TAG)
                .from(RECEIPTS.leftJoin(TAGS).on(RECEIPTS.ID.eq(TAGS.ID))).fetch();


        for (Record5 rec5: res) {
            list.add(new CombinationRecord(rec5.get(RECEIPTS.ID),
                    rec5.get(RECEIPTS.UPLOADED), rec5.get(RECEIPTS.MERCHANT),
                    rec5.get(RECEIPTS.AMOUNT), rec5.get(TAGS.TAG)));
        }

        return list;

    }
}
