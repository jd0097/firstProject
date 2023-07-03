package com.green.firstproject.memo;

import com.green.firstproject.memo.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memo")
@RequiredArgsConstructor
@Tag(name = "메모장")
public class MemoController {
    private final MemoService service;

    @PostMapping
    @Operation(summary = "메모장 타이틀, 내용 등록", description = "title : 제목<br>" + "ctnt : 내용<br>" + "iuser : 해당 유저 pk값")
    public int postMemo(@RequestBody MemoInsDto dto) {
        return service.insMemo(dto);
    }

    @PatchMapping("/titleCtnt")
    @Operation(summary = "메모장 타이틀, 내용 수정", description = "imemo : 메모장 PK값<br>" + "iuser : 해당 유저 pk값<br>" + "title : 제목<br>" +
            "ctnt : 내용")
    public int patchTileMemo(@RequestBody MemoUpAllDto dto) {
        return service.upTileMemo(dto);
    }

    @GetMapping("/allMemo")
    @Operation(summary = "모든 메모 보기")
    public List<MemoAllListVo> selAllMemo() {
        return service.selAllMemo();
    }

    @DeleteMapping
    @Operation(summary = "메모 삭제", description = "imemo : 메모장 pk값<br>")
    public int delMemo(@RequestBody MemoDelDto dto) {
        return service.delMemo(dto);
    }

}
