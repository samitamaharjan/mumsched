package edu.mum.mumsched.service;

import edu.mum.mumsched.model.Block;
import edu.mum.mumsched.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IBlockServiceImpl implements IBlockService{

    @Autowired
    private BlockRepository blockRepository;

    @Override
    public Block findById(Integer blockId) {
        return blockRepository.findOne(blockId);
    }

    @Override
    public void delete(Integer blockId) {
        blockRepository.delete(blockId);
    }

    @Override
    public void save(Block block) {
        blockRepository.save(block);
    }

    @Override
    public List<Block> findAll() {
        return blockRepository.findAll();
    }

    @Override
    public List<Block> findAllByOrdered() {
        return blockRepository.findAllByOrderByStartDateAsc();
    }

    @Override
    public Block findBlockByBlockNameAndSequenceNumber(String blockName, Integer seqNum) {
        return blockRepository.findBlockByBlockNameAndSequenceNumber(blockName, seqNum);
    }
}
