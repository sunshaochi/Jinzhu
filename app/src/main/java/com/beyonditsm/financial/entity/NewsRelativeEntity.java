package com.beyonditsm.financial.entity;

/**
 * Created by xuleyuan on 2016/10/11.
 */

public class NewsRelativeEntity {

    /**
     * downId : null
     * status : 0
     * title : 没有了
     * index : 1
     */

    private DownRowBean downRow;
    /**
     * status : 1
     * title : datasource2
     * upId : 5ef0b21b86db11e696d760eb69a5af72
     * index : 0
     */

    private UpRowBean upRow;
    /**
     * urlPath : www.baidu.com
     * title : hello1
     * pictrue : e338ba0c-b4eb-401a-9f0c-d621c622ec3c.png
     * description : <h1 class="ue_t" style="border-bottom-color:#cccccc;border-bottom-width:2px;border-bottom-style:solid;padding:0px 4px 0px 0px;text-align:center;margin:0px 0px 20px;"><span style="color:#c0504d;">[键入文档标题]</span></h1><p style="text-align:center;"><strong class="ue_t">[键入文档副标题]</strong></p><h3><span class="ue_t" style="font-family:幼圆">[标题 1]</span></h3><p class="ue_t" style="text-indent:2em;">对于“插入”选项卡上的库，在设计时都充分考虑了其中的项与文档整体外观的协调性。 您可以使用这些库来插入表格、页眉、页脚、列表、封面以及其他文档构建基块。 您创建的图片、图表或关系图也将与当前的文档外观协调一致。</p><h3><span class="ue_t" style="font-family:幼圆">[标题 2]</span></h3><p class="ue_t" style="text-indent:2em;">在“开始”选项卡上，通过从快速样式库中为所选文本选择一种外观，您可以方便地更改文档中所选文本的格式。 您还可以使用“开始”选项卡上的其他控件来直接设置文本格式。大多数控件都允许您选择是使用当前主题外观，还是使用某种直接指定的格式。</p><h3><span class="ue_t" style="font-family:幼圆">[标题 3]</span></h3><p class="ue_t">对于“插入”选项卡上的库，在设计时都充分考虑了其中的项与文档整体外观的协调性。 您可以使用这些库来插入表格、页眉、页脚、列表、封面以及其他文档构建基块。 您创建的图片、图表或关系图也将与当前的文档外观协调一致。</p><p class="ue_t"><br/></p><p><br/></p>
     * index : 2
     */

    private CurRowBean curRow;

    public DownRowBean getDownRow() {
        return downRow;
    }

    public void setDownRow(DownRowBean downRow) {
        this.downRow = downRow;
    }

    public UpRowBean getUpRow() {
        return upRow;
    }

    public void setUpRow(UpRowBean upRow) {
        this.upRow = upRow;
    }

    public CurRowBean getCurRow() {
        return curRow;
    }

    public void setCurRow(CurRowBean curRow) {
        this.curRow = curRow;
    }

    public static class DownRowBean {
        private Object downId;
        private int status;
        private String title;
        private int index;

        public Object getDownId() {
            return downId;
        }

        public void setDownId(Object downId) {
            this.downId = downId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class UpRowBean {
        private int status;
        private String title;
        private String upId;
        private int index;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpId() {
            return upId;
        }

        public void setUpId(String upId) {
            this.upId = upId;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static class CurRowBean {
        private String urlPath;
        private String title;
        private String pictrue;
        private String description;
        private int index;

        public String getUrlPath() {
            return urlPath;
        }

        public void setUrlPath(String urlPath) {
            this.urlPath = urlPath;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPictrue() {
            return pictrue;
        }

        public void setPictrue(String pictrue) {
            this.pictrue = pictrue;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
