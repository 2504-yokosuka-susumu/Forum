package com.example.forum.controller;

import com.example.forum.controller.form.CommentForm;
import com.example.forum.controller.form.ReportForm;
import com.example.forum.service.CommentService;
import com.example.forum.service.ReportService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
public class ForumController {
    @Autowired
    ReportService reportService;

    @Autowired
    CommentService commentService;

    @Autowired
    HttpSession session;

    /*
     * 投稿内容表示処理
     */
    @GetMapping
    public ModelAndView top(@ModelAttribute("errorMessages") List<String> errorMessages, @ModelAttribute("errorId") CommentForm errorId) {
        ModelAndView mav = new ModelAndView();

        // 投稿を全件取得
        List<ReportForm> contentData = reportService.findAllReport();
        List<CommentForm> commentData = commentService.findAllComment();

        CommentForm commentForm = new CommentForm();

        // セッションよりデータを取得して設定
        mav.addObject("errorMessages", errorMessages);
        mav.addObject("errorId", errorId.getReportId());

        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        mav.addObject("comments", commentData);
        mav.addObject("formModel", commentForm);
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/search")
    public ModelAndView search(@RequestParam(name = "startTime")String startTime,
                               @RequestParam(name = "endTime")String endTime) throws ParseException {
        ModelAndView mav = new ModelAndView();
        List<ReportForm> contentData = null;
        List<CommentForm> commentData = null;

        String start;
        String end;

        if (StringUtils.isNotBlank(startTime)) {
            start = startTime + " 00:00:00";
        } else {
            start = "2020-01-01 00:00:00";
        }
        if (StringUtils.isNotBlank(endTime)) {
           end = endTime + " 23:59:59";
        } else {
            Calendar cl = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            end = String.valueOf(sdf.format(cl.getTime()));
        }

        // 投稿を取得
        contentData = reportService.findDateReport(start, end);
        commentData = commentService.findDateComment(start, end);

        // 画面遷移先を指定
        mav.setViewName("/top");
        // 投稿データオブジェクトを保管
        mav.addObject("contents", contentData);
        mav.addObject("comments", commentData);
        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        ReportForm reportForm = new ReportForm();
        // 画面遷移先を指定
        mav.setViewName("/new");
        // 準備した空のFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 投稿編集画面表示
     */
    @GetMapping("/editContent/{id}")
    public ModelAndView editContent(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView();
        ReportForm reportForm = new ReportForm();
        // 編集する投稿を取得
        reportForm = reportService.editReport(id);
        // 画面遷移先を指定
        mav.setViewName("/editContent");
        // 準備したcontentのFormを保管
        mav.addObject("formModel", reportForm);
        return mav;
    }

    /*
     * 投稿編集画面表示
     */
    @GetMapping("/editComment/{id}")
    public ModelAndView editComment(@PathVariable Integer id){
        ModelAndView mav = new ModelAndView();
        CommentForm commentForm = new CommentForm();
        // 編集する投稿を取得
        commentForm = commentService.editComment(id);
        // 画面遷移先を指定
        mav.setViewName("/editComment");
        // 準備したcontentのFormを保管
        mav.addObject("formModel", commentForm);
        return mav;
    }

    /*
     * 新規投稿処理
     */
    @PostMapping("/add")
    public ModelAndView addContent(@ModelAttribute("formModel") @Valid ReportForm reportForm,
                                   BindingResult result, ModelAndView mav) {
        if(result.hasErrors()) {
            // 画面遷移先を指定
            mav.setViewName("/new");
            return mav;
        } else {
            // 投稿をテーブルに格納
            reportService.saveReport(reportForm);
            // rootへリダイレクト
            return new ModelAndView("redirect:/");
        }
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/deleteContent/{id}")
    public ModelAndView deleteContent(@PathVariable int id){
        ReportForm reportForm = new ReportForm();

        reportForm.setId(id);
        // 投稿をテーブルに格納
        reportService.deleteReport(reportForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿編集処理
     */
    @PostMapping("/updateContent/{id}")
    public ModelAndView updateContent(@PathVariable Integer id,
            @ModelAttribute("formModel") @Valid ReportForm reportForm, BindingResult result, ModelAndView mav) {

        reportForm.setId(id);
        if(result.hasErrors()) {
            // 画面遷移先を指定
            mav.setViewName("/editContent");
            return mav;
        } else {
            // 投稿をテーブルに格納
            reportService.saveReport(reportForm);
            // rootへリダイレクト
            return new ModelAndView("redirect:/");
        }
    }

    /*
     * コメント返信処理
     */
    @PostMapping("/comment")
    public ModelAndView commentContent(@ModelAttribute("formModel") @Valid CommentForm commentForm,
                                       BindingResult result, ModelAndView mav, RedirectAttributes redirectAttributes){

        if(result.hasErrors()) {

            List<String> errorList = new ArrayList<String>();

            for (ObjectError error: result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }

            redirectAttributes.addFlashAttribute("errorMessages", errorList);
            redirectAttributes.addFlashAttribute("errorId", commentForm);

            mav.setViewName("redirect:/");
            return mav;
        } else {
            // 投稿をテーブルに格納
            commentService.saveComment(commentForm);
            // rootへリダイレクト
            return new ModelAndView("redirect:/");
        }
    }

    /*
     * コメント編集処理
     */
    @PostMapping("/updateComment/{id}")
    public ModelAndView updateComment(@PathVariable Integer id,
                                      @ModelAttribute("formModel") @Valid CommentForm commentForm,
                                      BindingResult result, ModelAndView mav){

        if(result.hasErrors()) {
            // 画面遷移先を指定
            mav.setViewName("/editComment");
            return mav;
        } else {
            // 投稿をテーブルに格納
            commentService.saveComment(commentForm);
            // rootへリダイレクト
            return new ModelAndView("redirect:/");
        }
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/deleteComment/{id}")
    public ModelAndView deleteComment(@PathVariable int id){
        CommentForm commentForm = new CommentForm();

        commentForm.setId(id);
        // 投稿をテーブルに格納
        commentService.deleteComment(commentForm);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}
