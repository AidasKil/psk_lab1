package mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Category {
    @Getter
    @Setter
    private List<mybatis.model.Exercise> exercises = new ArrayList();
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLIC.CATEGORY.ID
     *
     * @mbg.generated Mon Apr 13 21:47:35 EEST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column PUBLIC.CATEGORY.NAME
     *
     * @mbg.generated Mon Apr 13 21:47:35 EEST 2020
     */
    private String name;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLIC.CATEGORY.ID
     *
     * @return the value of PUBLIC.CATEGORY.ID
     *
     * @mbg.generated Mon Apr 13 21:47:35 EEST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLIC.CATEGORY.ID
     *
     * @param id the value for PUBLIC.CATEGORY.ID
     *
     * @mbg.generated Mon Apr 13 21:47:35 EEST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column PUBLIC.CATEGORY.NAME
     *
     * @return the value of PUBLIC.CATEGORY.NAME
     *
     * @mbg.generated Mon Apr 13 21:47:35 EEST 2020
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column PUBLIC.CATEGORY.NAME
     *
     * @param name the value for PUBLIC.CATEGORY.NAME
     *
     * @mbg.generated Mon Apr 13 21:47:35 EEST 2020
     */
    public void setName(String name) {
        this.name = name;
    }
}