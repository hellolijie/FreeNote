package cn.mune.jerry.abc.entity;

/**
 * 生词实体
 * Created by lijie on 17/4/15.
 */

public class NewWordEntity {
    public String word;
    public String translate;

    @Override
    public int hashCode() {
        return 39;
    }

    @Override
    public boolean equals(Object o) {
        try {
            return word.equals(((NewWordEntity)o).word);
        }catch (Exception e){

        }

        return false;

    }
}
