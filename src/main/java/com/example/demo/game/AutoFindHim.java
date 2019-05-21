package com.example.demo.game;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取线路
 * G:起点到当前点的消耗（当前点G= 上一个点的G + 1）
 * H:当前点到终点的消耗（详见toGetH）
 * F:G+H
 * @author Administrator
 * 博客地址：https://blog.csdn.net/leq3915/article/details/80714902
 *
 */
public class AutoFindHim {
    public FKPosition beginFk = null;
    public FKPosition endFk = null;

    public  ArrayList<FKPosition> openList = new ArrayList<>();
    public  ArrayList<FKPosition> closedList = new ArrayList<>();


    /**
     * 获取路线方法入口
     * @param
     * @param
     * @return	路线集合List<FKPosition>
     */
    public List<FKPosition> getWayLine(EnemyTank cat, Tank fish){
        //定义返回的结果
        List<FKPosition> wayList = new ArrayList<>();
        //中心方块的四周方块集合
        List<FKPosition> tmpList = null;

        //将起点和终点转换为fangkuaiposition对象
        beginFk = new FKPosition(cat);
        beginFk.setG(0);
        endFk = new FKPosition(fish);

        //获取中心方块（起点）四周的方块

        tmpList = aroundFk(beginFk);
        //如果四周没有符合条件的方块，则说明是死路
        if(tmpList == null || tmpList.size() == 0){
            return wayList;
        }
        //放入openlist中，作为向外扩散的中心方块
       openList.addAll(tmpList);

        //遍历openlist，以每个方块作为中心方块，向外扩散
        for(int i = 0; i < openList.size(); i++){
            //获取新的中心方块，并获取四周方块
            FKPosition tmpFk =openList.get(i);
            tmpList = aroundFk(tmpFk);

            //周围方块为空，说明 是死路，继续下一个 方块
            if(tmpList == null || tmpList.size() == 0){
                //如果openlist已经遍历完了，都没有四周方块，则要在for循环外面判断waylist是否包含终点，
                //如果不包含，则到达不了终点
                continue;
            }

            //如果周围方块包括终点方块，则结束寻路
            if(tmpList.contains(endFk)){
                //如果四周方块包含终点，则将终点添加到closelist中，并跳出openlist循环（已经到达终点）
                for(FKPosition obj : tmpList){
                    if(obj.equals(endFk)){
                        closedList.add(obj);
                        break;
                    }
                }
                break;
            }

            /**
             * 将中心方块的周围方块添加到openlist集合
             * 注意：如果openlist中已经存在，则 需要将消耗最少的方块更新到 openlist中
             */
            for(FKPosition fk : tmpList){
                if(openList.contains(fk)){
                    for(FKPosition openFk : openList){
                        if(openFk.equals(fk)){
                            if(openFk.getG() > fk.getG()){
                                openFk.setG(fk.getG());
                                openFk.setF(openFk.getG() + openFk.getH());
                                openFk.setPreviousFK(fk.getPreviousFK());
                                break;
                            }
                        }
                    }
                }else{
                   openList.add(fk);
                }
            }

            //删掉openlist中的当前中心方块，继续获取并处理下一个
            openList.remove(i);
            i--;
        }

        /**
         * 从 closedlist中获取到路线
         * 先获取终点，然后根据fangkuaiposition.previousFk获取上一个方块，一直获取到起点
         */
        for(int i = 0; i < closedList.size(); i++){
            //如果wayList<=0,说明还没有获取到第一个方块(终点)；如果wayList>0,说明已经获取到第一个方块(终点)，则不用再执行下一个if语句
            if(wayList.size() > 0){
                FKPosition previousFK = wayList.get(wayList.size() - 1).getPreviousFK();
                if( previousFK !=null && closedList.get(i).equals(previousFK)){
                    wayList.add(closedList.get(i));
                    //如果获取到的方块是起点，则跳出for循环
                    if(closedList.get(i).equals(beginFk)){
                        break;
                    }
                    //获取到一个方块后，将该方块从closedlist中删除，然后从0开始遍历closedlist找到第一个方块的previousfk。
                    //所以需要赋值i=-1,因为continue的时候会执行一次i++
                    closedList.remove(closedList.get(i));
                    i = -1;
                }
                continue;
            }

            //第一个方块为终点，获取到一个方块后，将该方块从closedlist中删除，然后从0开始遍历closedlist找到第一个方块的previousfk。
            //所以需要赋值i=-1,因为continue的时候会执行一次i++
            if(closedList.get(i).equals(endFk)){
                wayList.add(closedList.get(i));
                closedList.remove(closedList.get(i));
                i = -1;
            }
        }

        return wayList;
    }

    public List<FKPosition> getWayLine(BulletOfTank cat, Tank2 fish){
        //定义返回的结果
        List<FKPosition> wayList = new ArrayList<>();
        //中心方块的四周方块集合
        List<FKPosition> tmpList = null;

        //将起点和终点转换为fangkuaiposition对象
        beginFk = new FKPosition(cat);
        beginFk.setG(0);
        endFk = new FKPosition(fish);

        //获取中心方块（起点）四周的方块

        tmpList = aroundFk(beginFk);
        //如果四周没有符合条件的方块，则说明是死路
        if(tmpList == null || tmpList.size() == 0){
            return wayList;
        }
        //放入openlist中，作为向外扩散的中心方块
       openList.addAll(tmpList);

        //遍历openlist，以每个方块作为中心方块，向外扩散
        for(int i = 0; i < openList.size(); i++){
            //获取新的中心方块，并获取四周方块
            FKPosition tmpFk =openList.get(i);
            tmpList = aroundFk(tmpFk);

            //周围方块为空，说明 是死路，继续下一个 方块
            if(tmpList == null || tmpList.size() == 0){
                //如果openlist已经遍历完了，都没有四周方块，则要在for循环外面判断waylist是否包含终点，
                //如果不包含，则到达不了终点
                continue;
            }

            //如果周围方块包括终点方块，则结束寻路
            if(tmpList.contains(endFk)){
                //如果四周方块包含终点，则将终点添加到closelist中，并跳出openlist循环（已经到达终点）
                for(FKPosition obj : tmpList){
                    if(obj.equals(endFk)){
                        closedList.add(obj);
                        break;
                    }
                }
                break;
            }

            /**
             * 将中心方块的周围方块添加到openlist集合
             * 注意：如果openlist中已经存在，则 需要将消耗最少的方块更新到 openlist中
             */
            for(FKPosition fk : tmpList){
                if(openList.contains(fk)){
                    for(FKPosition openFk : openList){
                        if(openFk.equals(fk)){
                            if(openFk.getG() > fk.getG()){
                                openFk.setG(fk.getG());
                                openFk.setF(openFk.getG() + openFk.getH());
                                openFk.setPreviousFK(fk.getPreviousFK());
                                break;
                            }
                        }
                    }
                }else{
                   openList.add(fk);
                }
            }

            //删掉openlist中的当前中心方块，继续获取并处理下一个
            openList.remove(i);
            i--;
        }

        /**
         * 从 closedlist中获取到路线
         * 先获取终点，然后根据fangkuaiposition.previousFk获取上一个方块，一直获取到起点
         */
        for(int i = 0; i < closedList.size(); i++){
            //如果wayList<=0,说明还没有获取到第一个方块(终点)；如果wayList>0,说明已经获取到第一个方块(终点)，则不用再执行下一个if语句
            if(wayList.size() > 0){
                if(wayList.get(wayList.size() - 1).getPreviousFK().equals(closedList.get(i))){
                    wayList.add(closedList.get(i));
                    //如果获取到的方块是起点，则跳出for循环
                    if(closedList.get(i).equals(beginFk)){
                        break;
                    }
                    //获取到一个方块后，将该方块从closedlist中删除，然后从0开始遍历closedlist找到第一个方块的previousfk。
                    //所以需要赋值i=-1,因为continue的时候会执行一次i++
                    closedList.remove(closedList.get(i));
                    i = -1;
                }
                continue;
            }

            //第一个方块为终点，获取到一个方块后，将该方块从closedlist中删除，然后从0开始遍历closedlist找到第一个方块的previousfk。
            //所以需要赋值i=-1,因为continue的时候会执行一次i++
            if(closedList.get(i).equals(endFk)){
                wayList.add(closedList.get(i));
                closedList.remove(closedList.get(i));
                i = -1;
            }
        }

        return wayList;
    }

    /**
     * 获取周围方块
     * ①判断是否超越边界
     * ②判断是否是障碍物/已计算过的方块
     * @param fk	中心方块
     * @return	周围方块结集合
     */
    public List<FKPosition> aroundFk(FKPosition fk){
        if(fk.getX() == 10 && fk.getY() == 11){
            System.out.println(".....");
        }
        List<FKPosition> list = new ArrayList<FKPosition>();
        //判断上面的方块是否符合条件
        //判断是否超过越边界
        if(fk.getY() - 1 >= 0){
            FKPosition tmpFk = new FKPosition(fk.getX(), fk.getY() - 1, fk);
            //判断是否是障碍物/已计算过的方块
            if(!GameWindow.zhangaiList.contains(tmpFk)
                    && !closedList.contains(tmpFk)){
                list.add(tmpFk);
            }
        }

        //判断下面的方块是否符合条件
        if(fk.getY() + 1 < Config.HEIGHT/ Config.SIZE){
            FKPosition tmpFk = new FKPosition(fk.getX(), fk.getY() + 1, fk);
            if(!GameWindow.zhangaiList.contains(tmpFk)
                    && !closedList.contains(tmpFk)){
                list.add(tmpFk);
            }
        }

        //判断左面的方块是否符合条件
        if(fk.getX() - 1 >= 0){
            FKPosition tmpFk = new FKPosition(fk.getX() - 1, fk.getY(), fk);
            if(!GameWindow.zhangaiList.contains(tmpFk)
                    && !closedList.contains(tmpFk)){
                list.add(tmpFk);
            }
        }
        //判断右面的方块是否符合条件
        if(fk.getX() + 1 < Config.WIDTH/ Config.SIZE){
            FKPosition tmpFk = new FKPosition(fk.getX() + 1, fk.getY(), fk);
            if(!GameWindow.zhangaiList.contains(tmpFk)
                    && !closedList.contains(tmpFk)){
                list.add(tmpFk);
            }
        }

        //将中心方块添加到已处理过的集合中
       closedList.add(fk);
//        getFGH(list,fk);
        return list;
    }

    /**
     * 给集合中的每个方块计算出FGH的值
     * @param list
     */
    public void getFGH(List<FKPosition> list, FKPosition currFk){
        if(list != null && list.size() > 0){
            for(FKPosition fk : list){
                fk.setG(currFk.getG() + 1);
                fk.setH(toGetH(fk,endFk));
                fk.setF(fk.getG() + fk.getH());
            }
        }
    }

    /**
     * 获取从一个方块到另一个方块的移动量（按方块个数计算）
     * @param
     * @param targetFangKuai
     * @return
     */
    public int toGetH(FKPosition currentFangKuai, FKPosition targetFangKuai){
        int h = 0;
        h += Math.abs(currentFangKuai.getX() - targetFangKuai.getX());
        h += Math.abs(currentFangKuai.getY() - targetFangKuai.getY());
        return h;
    }

}


