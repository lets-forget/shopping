package com.ning.home_admin.controller;

import com.ning.home_admin.bean.Comment;
import com.ning.home_admin.bean.Order;
import com.ning.home_admin.bean.ov.CommentPageResult;
import com.ning.home_admin.commons.exception.LoginException;
import com.ning.home_admin.commons.utils.FastDFSClient;
import com.ning.home_admin.commons.utils.GeneralResultInfo;
import com.ning.home_admin.service.CommentService;
import com.ning.home_admin.sytem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    Integer id;
    //查询评论
    @PostMapping("/selectAll")
    public CommentPageResult selectAll(Integer currentPage, Integer limit, Integer details_id){
        CommentPageResult info= commentService.findByPageComment(currentPage,limit,details_id);
        return info;
    }
    //添加评论
    @PostMapping("/connect")
    public GeneralResultInfo connent(Comment comment,HttpSession session) throws LoginException {
        try {
            User user = (User) session.getAttribute("user");
            commentService.insertComment(comment,user);
            id=comment.getCommentId();
        }catch (Exception e){
            throw new LoginException("评论失败");
        }

        return new GeneralResultInfo().success();
    }
    //是否购买
    @PostMapping("/ispurchase")
    public GeneralResultInfo ispurchase(Integer detailsId,HttpSession session) throws LoginException {
        User user = (User) session.getAttribute("user");
        List<Order> order=commentService.ispurchase(user.getUserId(),detailsId);
        if (CollectionUtils.isEmpty(order))
            throw new LoginException("没有评论");

        return new GeneralResultInfo().success();
    }
    //上传评论的图片
    @PostMapping("/uploadImage")
     public GeneralResultInfo uploadImage(Integer details_id, MultipartFile  file, HttpSession session) throws Exception {
        //判断文件是否是图片
       BufferedImage image = ImageIO.read(file.getInputStream());
        if (image==null)
            throw new LoginException("文件类型不符合，请上传图片类型");

        try {
            //保存文件
            String path = FastDFSClient.saveFile(file);
            User user = (User) session.getAttribute("user");
            commentService.updateImage(user.getId(),details_id,path,id);
        } catch (IOException e) {
            throw new LoginException("上传图片失败");
        }
        return new GeneralResultInfo().success();
    }
    /**
     * 不使用fastdfs
     * @PostMapping("/uploadImage")
     *      public GeneralResultInfo uploadImage(Integer details_id, MultipartFile  file, HttpSession session) throws Exception {
     *         //判断文件是否是图片
     *        BufferedImage image = ImageIO.read(file.getInputStream());
     *         if (image==null)
     *             throw new LoginException("文件类型不符合，请上传图片类型");
     *         //获取原始的文件名
     *         String fileName = file.getOriginalFilename();
     *         String newFileName=fileName.substring(fileName.indexOf("."),fileName.length());
     *
     *         //保存文件
     *         try {
     *             //获取跟目录
     *             //获取项目classes/static的地址
     *             String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
     *             //文件名
     *             String imageName= "/commentImage/" +uuidUtils.getUUID()+newFileName;
     *             // 绝对路径=项目路径+自定义路径
     *             File upload = new File(path+imageName);
     *             //拷贝图片
     *             file.transferTo(upload);
     *             User user = (User) session.getAttribute("user");
     *             commentService.updateImage(user.getId(),details_id,imageName,id);
     *         } catch (IOException e) {
     *             throw new LoginException("上传图片失败");
     *         }
     *         return new GeneralResultInfo().success();
     *     }
     */
}
