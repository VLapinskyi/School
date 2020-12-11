package ua.com.foxminded.domain.data_generator;

import java.util.ArrayList;
import java.util.Random;

import ua.com.foxminded.dao.GroupDAO;

class GroupsGenerator {
	public void writeTenGroups () {
		ArrayList<String> listWithRandomNames = generateTenNames();
		for (int i = 0; i < listWithRandomNames.size(); i++) {
			GroupDAO groupDAO = new GroupDAO();
			groupDAO.writeGroup(listWithRandomNames.get(i));
		}
		
	}
	
	private ArrayList<String> generateTenNames() {
		ArrayList<String> listWithRandomeNames = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			StringBuilder groupName = new StringBuilder();
			Random random = new Random();
			char firstLetter = (char) (random.nextInt(26) + 65);
			char secondLetter = (char) (random.nextInt(26) + 65);
			int firstNumber = random.nextInt(10);
			int secondNumber = random.nextInt(9)+1;
			groupName.append(firstLetter);
			groupName.append(secondLetter);
			groupName.append('-');
			groupName.append(firstNumber);
			groupName.append(secondNumber);
			listWithRandomeNames.add(groupName.toString());
		}
		return listWithRandomeNames;
	}
}
