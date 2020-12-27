package ua.com.foxminded.ui.data_generator;

import java.util.ArrayList;
import java.util.Random;

import ua.com.foxminded.domain.University;

class GroupsGenerator {
	private static Random random = RandomInstance.getRandomInstance();
	private University university;
	
	public GroupsGenerator(University university) {
		this.university = university;
	}

	public void createTenGroups () {
		ArrayList<String> listWithRandomNames = generateTenNames();
		listWithRandomNames.stream().forEach(university :: addGroup);
	}

	private ArrayList<String> generateTenNames() {
		ArrayList<String> listWithRandomeNames = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			StringBuilder groupName = new StringBuilder();
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
