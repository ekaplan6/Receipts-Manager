package controllers;

import api.CombinationResponse;
import api.CreateReceiptRequest;
import dao.ReceiptDao;
import generated.tables.records.CombinationRecord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/receipts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceiptController {
    final ReceiptDao receipts;

    public ReceiptController(ReceiptDao receipts) {
        this.receipts = receipts;
    }

    @POST
    public int createReceipt(@Valid @NotNull CreateReceiptRequest receipt) {
        return receipts.insert(receipt.merchant, receipt.amount);
    }

    @GET
    public List<CombinationResponse> getReceipts() {
        List<CombinationRecord> combinationRecords = receipts.getAllReceipts();
        return combinationRecords.stream().map(CombinationResponse::new).collect(toList());
    }
}
