package com.codejies.lyb.bean;

import java.util.List;

/**
 * Created by Jies on 2018/8/17.
 */

public class MeiziResult {
    List<Meizi> data;

    public List<Meizi> getData() {
        return data;
    }

    public void setData(List<Meizi> data) {
        this.data = data;
    }

    public class Meizi {

        /**
         * path : https://wx3.sinaimg.cn/mw600/0076BSS5ly1fubxgz0terj31ao1ao4m3.jpg
         * created_at : 2018-08-17 11:32:01
         * id : 1140
         */
        private String path;
        private String created_at;
        private int id;

        public void setPath(String path) {
            this.path = path;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public String getCreated_at() {
            return created_at;
        }

        public int getId() {
            return id;
        }
    }
}
