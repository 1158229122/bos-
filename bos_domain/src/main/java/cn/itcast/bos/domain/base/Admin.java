package cn.itcast.bos.domain.base;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Jim-gongtiancheng
 * @create 2019 - 01 - 09 20:33
 */
@Entity
@Table(name = "T_ADMIN")
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "A_ID")
    private Integer id;
    @Column(name = "A_USERNAME")
    private String username;//名户名
    @Column(name = "A_PASSWORD")
    private String password;//密码
    @Column(name = "A_ROLE")
    private String role;//角色
    @Column(name = "A_NAME")
    private String name;//名字
    @Column(name = "A_URL")
    private String url;//地址
}
