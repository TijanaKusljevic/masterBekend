package etf.master.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import etf.master.dao.TourDAO;
import etf.master.entity.Tour;
import etf.master.entity.Users;

@Service
public class TourService {
	
	private TourDAO tourDAO;
	
	@Autowired
	public TourService(TourDAO tourDAO) {
		this.tourDAO = tourDAO;
	}
	
	@Transactional
    public void saveTour(Tour tour) {
    	tourDAO.saveTour(tour);
    }
	
	@Transactional
    public List<Tour> findAll() {
    	return tourDAO.findAll();
    }
}
