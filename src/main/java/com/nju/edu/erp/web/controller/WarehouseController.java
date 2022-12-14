package com.nju.edu.erp.web.controller;

import com.nju.edu.erp.auth.Authorized;
import com.nju.edu.erp.enums.Role;
import com.nju.edu.erp.enums.sheetState.WarehouseInputSheetState;
import com.nju.edu.erp.enums.sheetState.WarehouseOutputSheetState;
import com.nju.edu.erp.exception.MyServiceException;
import com.nju.edu.erp.model.po.*;
import com.nju.edu.erp.model.vo.UserVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseCountingVO;
import com.nju.edu.erp.model.vo.warehouse.GetWareProductInfoParamsVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseInputFormVO;
import com.nju.edu.erp.model.vo.warehouse.WarehouseOutputFormVO;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.ExcelUtil;
import com.nju.edu.erp.web.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/warehouse")
public class WarehouseController {

    public WarehouseService warehouseService;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }


    @PostMapping("/product/count")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER})
    public Response warehouseOutput(@RequestBody GetWareProductInfoParamsVO getWareProductInfoParamsVO) {
        return Response.buildSuccess(warehouseService.getWareProductInfo(getWareProductInfoParamsVO));
    }


    @GetMapping("/inputSheet/approve")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER})
    public Response warehouseInputSheetApprove(UserVO user,
                                               @RequestParam(value = "sheetId") String sheetId,
                                               @RequestParam(value = "state") WarehouseInputSheetState state) {
        if (state.equals(WarehouseInputSheetState.FAILURE) || state.equals(WarehouseInputSheetState.SUCCESS)) {
            warehouseService.approvalInputSheet(user, sheetId, state);
        } else {
            throw new MyServiceException("C00001", "???????????????");
        }
        return Response.buildSuccess();
    }

    @GetMapping("/inputSheet/state")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER})
    public Response getWarehouseInputSheet(@RequestParam(value = "state", required = false) WarehouseInputSheetState state) {
        List<WarehouseInputSheetPO> ans = warehouseService.getWareHouseInputSheetByState(state);
        return Response.buildSuccess(ans);
    }


    @GetMapping("/outputSheet/state")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER, Role.GM})
    public Response getWarehouseOutSheet(@RequestParam(value = "state", required = false) WarehouseOutputSheetState state) {
        List<WarehouseOutputSheetPO> ans = warehouseService.getWareHouseOutSheetByState(state);
        return Response.buildSuccess(ans);
    }

    @GetMapping("/inputSheet/content")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER, Role.GM})
    public Response getWarehouseInputSheetContent(@RequestParam(value = "sheetId") String sheetId) {
        List<WarehouseInputSheetContentPO> ans = warehouseService.getWHISheetContentById(sheetId);
        return Response.buildSuccess(ans);
    }

    @GetMapping("/outputSheet/content")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER, Role.GM})
    public Response getWarehouseOutputSheetContent(@RequestParam(value = "sheetId") String sheetId) {
        List<WarehouseOutputSheetContentPO> ans = warehouseService.getWHOSheetContentById(sheetId);
        return Response.buildSuccess(ans);
    }

    @GetMapping("/outputSheet/approve")
    @Authorized(roles = {Role.ADMIN, Role.GM, Role.INVENTORY_MANAGER})
    public Response warehouseOutputSheetApprove(UserVO user,
                                                @RequestParam(value = "sheetId") String sheetId,
                                                @RequestParam(value = "state") WarehouseOutputSheetState state) {
        if (state.equals(WarehouseOutputSheetState.FAILURE) || state.equals(WarehouseOutputSheetState.SUCCESS)) {
            warehouseService.approvalOutputSheet(user, sheetId, state);
        } else {
            throw new MyServiceException("C00001", "???????????????");
        }
        return Response.buildSuccess();
    }


    /**
     * ??????????????????????????????????????????/????????????/??????/????????????/????????????
     *
     * @param beginDateStr ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @param endDateStr   ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @return
     */
    @GetMapping("/sheetContent/time")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER})
    public Response getWarehouseIODetailByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        List<WarehouseIODetailPO> ans = warehouseService.getWarehouseIODetailByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }

    /**
     * ????????????????????????/?????????/???????????????
     *
     * @param beginDateStr ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @param endDateStr   ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @return
     */
    @GetMapping("/specialSheetContent/time")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER, Role.FINANCIAL_STAFF, Role.GM})
    public Response getWarehouseSpecialIODetailByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        List<WarehouseSpecialIODetailPO> ans = warehouseService.getWarehouseSpecialIODetailByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(ans);
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param beginDateStr ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @param endDateStr   ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @return
     */
    @GetMapping("/inputSheet/time/quantity")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER})
    public Response getWarehouseInputProductQuantityByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int quantity = warehouseService.getWarehouseInputProductQuantityByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(quantity);
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param beginDateStr ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @param endDateStr   ????????????yyyy-MM-dd HH:mm:ss????????????2022-05-12 11:38:30???
     * @return
     */
    @GetMapping("/outputSheet/time/quantity")
    @Authorized(roles = {Role.ADMIN, Role.INVENTORY_MANAGER})
    public Response getWarehouseOutputProductQuantityByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) throws ParseException {
        int quantity = warehouseService.getWarehouseOutProductQuantityByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(quantity);
    }

    /**
     * ????????????
     * ??????????????????????????????????????????????????????????????????
     * ??????????????????????????????????????????????????????????????????????????????????????????????????????
     * ??????????????????Excel
     */
    @GetMapping("/warehouse/counting")
    public Response getWarehouseCounting() {
        List<WarehouseCountingVO> warehouseCountingVOS = warehouseService.warehouseCounting();
        return Response.buildSuccess(warehouseCountingVOS);
    }

    /**
     * ????????????
     *
     * @return
     */
    @GetMapping("/warehouse/present")
    public Response getWarehousePresent() {
        List<WarehouseZSDPO> warehousePresent = warehouseService.getWarehousePresent();
        return Response.buildSuccess(warehousePresent);
    }


    /**
     * ????????????
     *
     * @return
     */
    @GetMapping("/warehouse/overflow")
    public Response getWarehouseOverflow() {
        List<WarehouseBYDPO> warehouseOverflow = warehouseService.getWarehouseOverflow();
        return Response.buildSuccess(warehouseOverflow);
    }

    /**
     * ????????????????????????????????????
     *
     * @param beginDateStr ????????????
     * @param endDateStr   ????????????
     * @return ????????????
     */
    @GetMapping("/warehouse/overflowTotal")
    public Response getOverflowByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        int overflowByTime = warehouseService.getOverflowByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(overflowByTime);
    }


    /**
     * ????????????
     *
     * @return
     */
    @GetMapping("/warehouse/loss")
    public Response getWarehouseLoss() {
        List<WarehouseBSDPO> warehouseLoss = warehouseService.getWarehouseLoss();
        return Response.buildSuccess(warehouseLoss);
    }

    /**
     * ????????????????????????????????????
     *
     * @param beginDateStr ????????????
     * @param endDateStr   ????????????
     * @return ????????????
     */
    @GetMapping("/warehouse/lossTotal")
    public Response getLossByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        int lossByTime = warehouseService.getLossByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(lossByTime);
    }

    /**
     * ????????????????????????????????????
     *
     * @param beginDateStr ????????????
     * @param endDateStr   ????????????
     * @return ????????????
     */
    @GetMapping("/warehouse/presentTotal")
    public Response getPresentByTime(@RequestParam String beginDateStr, @RequestParam String endDateStr) {
        int presentByTime = warehouseService.getPresentByTime(beginDateStr, endDateStr);
        return Response.buildSuccess(presentByTime);
    }

    @GetMapping("/warehouse/exportExcel")
    public void ExportExcel(HttpServletResponse response) {
        List<WarehouseCountingVO> warehouseCountingVOS = warehouseService.warehouseCounting();
        try {
            ExcelUtil.exportExcel(warehouseCountingVOS, "????????????", "sheet1", WarehouseCountingVO.class, "????????????", response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
