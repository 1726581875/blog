package com.smallchili.blog.service.impl;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.smallchili.blog.dto.ArticleHeadMsgDTO;
import com.smallchili.blog.dto.ArticleUserDetail;
import com.smallchili.blog.repository.ArticleUserDTORepository;
import com.smallchili.blog.service.StatisticalService;
import com.smallchili.blog.utils.ArticleUtil;
import com.smallchili.blog.vo.ArticleHeadPageVO;

/**
* @author xmz
* 2020-04-06
* 
*/
@Service
public class StatisticalServiceImpl implements StatisticalService{

	@Autowired
	private ArticleUserDTORepository articleUserDTORepository;
    //配置页数
	@Value("${pageSize}")
	private Integer pageSize;
	private static List<ArticleUserDetail> articleUserList;
	
	public void findAndSortArticle(){
		//查出来
	    articleUserList = articleUserDTORepository.findAll();
	    //排序
		articleUserList.forEach(System.out::println);
		articleUserList.sort(Comparator.comparingInt(e -> 
	    ((ArticleUserDetail) e).getArticleView()
	    + ((ArticleUserDetail) e).getArticleStar()*10
	    + ((ArticleUserDetail) e).getCommentCount()*30).reversed());
		
	}
	
	
	@Override
	public ArticleHeadPageVO statisticalRank(Integer page) {
        //如果查询第一页才去执行算法,为了尽量减少重复加载
		if(page == 1){
			findAndSortArticle();
		}
		
	    int subSize = pageSize;
        int subCount = articleUserList.size();
        int subPageTotal = (subCount / subSize) + ((subCount % subSize > 0) ? 1 : 0);
        // 分页计算
        int fromIndex = (page - 1) * subSize;
        int toIndex = ((page  == subPageTotal) ? subCount : ((page) * subSize));
        //截取对应段
        List<ArticleUserDetail> someArticleList = articleUserList.subList(fromIndex, toIndex);
        List<ArticleHeadMsgDTO> articleHeadList = ArticleUtil.conversionArticleList(someArticleList);
        ArticleHeadPageVO ArticlePageVO = new ArticleHeadPageVO();
        ArticlePageVO.setContent(articleHeadList);
        ArticlePageVO.setTotalPages(subPageTotal);
        ArticlePageVO.setNowPage(page);
        
		return ArticlePageVO;
	}

}
