package cn.edu.jnu.baiscms.controller;

import cn.edu.jnu.baiscms.common.Result;
import cn.edu.jnu.baiscms.entity.Obj;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//public class HelloController {
//    @RequestMapping
//    public String hello(){
//        return "hellllo";
//    }
//    @RequestMapping("/test")
//    public Result test(String data){
//        return Result.success(data);
//    }
//}

@RestController
@RequestMapping("/web")
public class HelloController {
    @RequestMapping(value = "/get1", method = RequestMethod.GET)
    public Result get1(String data){
        return Result.success(data);
    }
    @GetMapping("/test")
    public Result test(String data){
        return Result.success(data);
    }
//     POST接口方式1
    @RequestMapping(method = RequestMethod.POST)
    public String hello(){
        return "Halo";
    }
//    POST接口方式2
    @PostMapping("/post")
    public Result post (@RequestBody Obj obj){ //RequestBody要求传输json格式数据requestbody
        return Result.success(obj);
    }

    @PutMapping("/put")
    public Result put(@RequestBody Obj obj){
        return Result.success(obj);
    }

//    @DeleteMapping("/delete/{id}")  //id路径参数
//    public Result delete(@PathVariable Integer id){
//        return Result.success(id);
//    }

    @DeleteMapping("/delete") //接受json数据，用于批量删除
    public Result delete(@RequestBody List<Integer> ids){
        return Result.success(ids);
    }
}
