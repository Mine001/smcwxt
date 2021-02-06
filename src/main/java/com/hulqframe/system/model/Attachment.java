package com.hulqframe.system.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "s_attachment")
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter @Getter
    private Long id;

    /**
     * 文件名称
     */
    @Column(name = "file_name")
    @Setter @Getter
    private String fileName;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    @Setter @Getter
    private Integer userId;

    /**
     * 用户名
     * */
    @Setter @Getter
    @Column(name = "username")
    private String username;

    /**
     * 上传时间
     */
    @Column(name = "upload_date")
    @Setter @Getter
    private Date uploadDate;

    /**
     * 上传的ID
     */
    @Column(name = "upload_ip")
    @Setter @Getter
    private String uploadIp;

    /**
     * 文件扩展名
     */
    @Column(name = "file_extname")
    @Setter @Getter
    private String fileExtname;

    /**
     * 文件路径
     */
    @Column(name = "file_path")
    @Setter @Getter
    private String filePath;

    /**
     * 文件大小
     */
    @Column(name = "file_size")
    @Setter @Getter
    private Float fileSize;


    /**
     * 附件网址
     */
    @Column(name = "url")
    @Setter @Getter
    private String url;

    @Column(name = "original_file_name")
    @Setter @Getter
    private String originalFilename;

    /**
     * key
     */
    @Column(name = "file_key")
    @Setter @Getter
    private String fileKey;

    /**
     *  业务类型
     */
    @Column(name = "biz_type")
    @Setter @Getter
    private String bizType;
    /**
     * 业务ID
     */
    @Column(name = "biz_id")
    @Setter @Getter
    private String bizId;
	
	@Transient
	public String getDownLoadUrl() {
		return "/res/"+this.getFileKey()+this.getFileName().substring(this.getFileName().lastIndexOf("."));
	}
    
}