/*
 * Copyright   Loy Fu. 付厚俊
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/
package com.loy.log.service.impl;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.util.TableToExcelUtil;
import com.loy.log.domain.LogQueryParam;
import com.loy.log.domain.entity.OperatorLogEntity;
import com.loy.log.repository.OperatorLogRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import com.loy.e.common.annotation.Author;

@Author(author = "Loy Fu", website = "http://www.17jee.com", contact = "qq群 540553957")

@RestController
@RequestMapping(value = "**/operation", method = { RequestMethod.POST, RequestMethod.GET })
@Transactional
@Api(value = "操作日志", description = "操作日志")
public class OperatorLogImplService {
    @Autowired
    OperatorLogRepository operatorLogRepository;

    @RequestMapping(value = "/page")

    @ApiOperation(value = "查询操作日志", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "操作名称", paramType = "form", dataType = "string"),

            @ApiImplicitParam(name = "pageNumber", value = "页号", paramType = "form", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "页的大小", paramType = "form", dataType = "int")
    })
    public Page<OperatorLogEntity> queryPage(@ApiIgnore LogQueryParam logQueryParam,
            @ApiIgnore Pageable pageable) {
        Page<OperatorLogEntity> page = operatorLogRepository.findLogPage(logQueryParam, pageable);
        return page;
    }

    @RequestMapping(value = "/excel", method = { RequestMethod.POST })
    @ControllerLogExeTime(description = "导出操作日志", log = false)
    @ApiIgnore
    public void excel(String html, HttpServletResponse response) throws IOException {
        response.setContentType("application/msexcel;charset=UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=logs.xls");
        OutputStream out = response.getOutputStream();
        TableToExcelUtil.createExcelFormTable("log", html, 1, out);
        out.flush();
        out.close();
    }
}
